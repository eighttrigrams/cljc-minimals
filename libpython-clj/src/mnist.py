from tensorflow import keras
from tensorflow.keras import layers
import numpy as np

num_classes = 10
input_shape = (28, 28, 1)

def model():
    model = keras.Sequential(
        [
            keras.Input(shape=input_shape),
            layers.Conv2D(32, kernel_size=(3, 3), activation="relu"),
            layers.MaxPooling2D(pool_size=(2, 2)),
            layers.Conv2D(64, kernel_size=(3, 3), activation="relu"),
            layers.MaxPooling2D(pool_size=(2, 2)),
            layers.Flatten(),
            layers.Dropout(0.5),
            layers.Dense(num_classes, activation="softmax"),
        ]
    )
    model.compile(loss="categorical_crossentropy", optimizer="adam", metrics=["accuracy"])
    return model

def train(model, xss, ys):
    xss = np.array(xss).reshape(128, 28, 28, 1)
    ys = np.array(ys)
    ys = keras.utils.to_categorical(ys, num_classes)
    model.fit(xss, ys, batch_size=128, validation_split=0.1)
