# Base image
FROM php:8.2-fpm

# Install dependencies
RUN apt-get update && apt-get install -y \
    libpng-dev \
    libjpeg-dev \
    libfreetype6-dev \
    zip \
    unzip \
    curl \
    git \
    && docker-php-ext-install pdo pdo_mysql gd

# Install Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Set working directory
WORKDIR /var/www

# Copy only composer files first for caching
COPY composer.json composer.lock /var/www/

# Install dependencies
RUN composer install --no-scripts --no-dev --prefer-dist --optimize-autoloader

# Copy application
COPY . /var/www

# Set permissions
RUN chown -R www-data:www-data /var/www && chmod -R 755 /var/www

# Expose port for Cloud Run
EXPOSE 8000

# Start the PHP server
CMD php artisan serve --host=0.0.0.0 --port=${PORT:-8000}
