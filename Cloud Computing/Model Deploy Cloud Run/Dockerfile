# Gunakan base image resmi Python 3.9
FROM python:3.9-slim

# Set environment variable untuk mencegah buffering pada log
ENV PYTHONUNBUFFERED=1

# Buat direktori kerja untuk aplikasi
WORKDIR /app

# Salin file requirements.txt untuk instalasi dependensi
COPY requirements.txt /app/

# Install dependensi Python
RUN pip install --no-cache-dir -r requirements.txt

# Salin semua file ke dalam container
COPY . /app/

# Ekspos port 8080 untuk Cloud Run
EXPOSE 8080

# Jalankan aplikasi Flask
CMD ["gunicorn", "--bind", "0.0.0.0:8080", "app:app"]
