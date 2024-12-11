import tensorflow as tf

def convert_h5_to_tflite(h5_model_path, tflite_model_path):
    #load model
    model = tf.keras.models.load_model(h5_model_path)
    #konverter untuk model keras
    converter = tf.lite.TFLiteConverter.from_keras_model(model)  
    #convert model
    tflite_model = converter.convert()
    #save tflite
    with open(tflite_model_path, 'wb') as f:
        f.write(tflite_model)
    
    print(f"Model converted and saved to {tflite_model_path}")

#jalankan function
convert_h5_to_tflite('disease_model.h5', 'disease_model.tflite')