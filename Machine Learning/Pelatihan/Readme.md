## Machine Learning
### Disease Prediction System 🩺

#### Dataset Link
- [Dataset Machine Learning]([https://github.com/zendParadox/journey-ml](https://drive.google.com/drive/folders/1par6U9mkVH2NsmkNEq8ScZqz6apKsG4M?usp=drive_link)

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
