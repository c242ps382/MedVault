# Capstone Project Bangkit 2024

## About The Project

![Profile Picture](https://storage.googleapis.com/medvault-profile-picture/your-path-prefix/image-removebg-preview.png)

MedVault, a simplified and user-friendly electronic medical record (EMR) application tailored specifically for independent midwives. This solution addresses the challenges of manual data management, enhancing the accuracy and efficiency of patient record-keeping while ensuring compliance with healthcare regulations. MedVault features AI-driven diagnosis recommendations to aid midwives in making informed decisions and secure cloud-based data storage for easy access and reliable data protection.

---

## Team Members

**Team ID:** C242-PS382

|          Nama         | Bangkit-ID    |       Path       |
|:---------------------:|:-------------:|:----------------:|
| Fatih Maulana Gibran  | M623B4KY1438  | Machine Learning |
| Marshanda             | M197B4KX2418  | Machine Learning |
| Wanda Azilla          | M134B4KX4465  | Machine Learning |
| Hilwa Izzatinnafisah  | C134B4KX1790  | Cloud Computing  |
| Aditya Dwi Cahyo      | C134B4KY0110  | Cloud Computing  |
| Khoirun Niswati Ulya  | A134B4KX2214  | Mobile Development|
| Rindi Afriani         | A134B4KX3866  | Mobile Development|

---

## Machine Learning

### Disease Prediction System 🩺

#### 📝 Project Description

This project develops a machine learning-based disease prediction system using TensorFlow, capable of identifying diseases based on patient symptoms.

#### 🎯 Project Objectives

- Predict diseases based on a set of symptoms
- Provide probability predictions for each disease
- Utilize ICD (International Classification of Diseases) codes for classification

#### 📚 Machine Learning Concept

- Artificial Neural Network
- Deep Learning with TensorFlow and Keras
- Multi-class classification technique
- Symptom-based prediction algorithm

#### 🗂 Dataset

- **Filename**: diseases_ICD_indonesia.csv
- **Total Entries**: 246,033 rows
- **Main Attributes**: Disease Name, ICD Code, Various Clinical Symptoms (378 features)

#### 📊 Dataset Split

- **Training Set**: 80%
- **Testing Set**: 20%

#### 🧰 Technologies and Requirements

- TensorFlow
- NumPy
- Scikit-learn
- Keras
- Flask
- Pickle

#### 🏗 Model Architecture

- Input Layer: 378 symptom features
- Hidden Layers: Dense Layers (512, 256, 128 neurons)
- Output Layer: Softmax for multi-class classification

#### 🔍 Development Techniques

- Batch Normalization
- Dropout to prevent overfitting
- Early Stopping
- Adam Optimizer

#### 🏃‍♂️ Training Process

- **Epochs**: 25
- **Batch Size**: 32
- **Optimizer**: Adam
- **Loss Function**: Categorical Crossentropy

#### 📊 Model Evaluation

- **Training Accuracy**: 85.60%
- **Validation Accuracy**: 85.83%
- **Validation Loss**: 0.3570

#### 💾 Output Components

1. **Machine Learning Model**: 
   - Format: H5 (disease_model.h5)
   - Conversion: TFLite (disease_model.tflite)
2. **Additional Mappings**:
   - Disease Mapping (diseases_mapping.csv)
   - Symptom Mapping (symptoms_mapping.csv)
   - Label Encoder (label_encoder.pkl)

#### 🚀 Prediction Endpoint

- **Method**: POST
- **Input**: List of symptoms
- **Output**:
  - Top 3 Disease Predictions
  - Disease Name
  - ICD Code
  - Probability

#### 🔍 API Usage

1. Send JSON request with symptom list
2. Receive response with disease predictions
3. Analyze disease probabilities and codes

#### 🤝 Contributions

We welcome contributions for:

- Model improvements
- Dataset expansion
- Algorithm optimization

---

## Cloud Computing

### Featured Technologies

- [Google Cloud Platform (GCP)](https://cloud.google.com/gcp/): Grow from prototype to production without having to think about capacity, reliability, or performance. Easily capture, manage, process, and visualize data with Google Cloud data analytics products.
- [Laravel](https://laravel.com): A powerful PHP framework known for its simplicity, scalability, and speed. Laravel is ideal for building robust, maintainable, and secure backend systems for applications like MedVault. It offers built-in features such as database migrations, authentication, and routing, which streamline development and ensure data integrity.

### API Documentation

[Postman Documentation](https://documenter.getpostman.com/view/39643583/2sAYHxm3V7)

---

## Mobile Development

### Features

- **Login and Register**: Authentication for users
- **Adding Patient Data**: Add patient personal data with CRUD operations
- **Medical or Non-Medical**: Choose features based on needs
- **Symptom Input for Diagnosis**: Enter symptoms for system diagnosis
- **Diagnosis Results**: View detailed uploaded diagnoses
- **Non-Medical Visit Records**: Manage non-medical visits
- **Health News**: View health-related news on the home page

### Dependencies

- Retrofit 2
- Dagger Hilt
- CircleImageView
- Retrofit 2 Converter-Gson
- OkHttp3
- RecyclerView

### Tools

- Android Studio Koala 2024.1.2
- JRE or JDK
