(ns jimprint.settings
  (:require [clojurefx.core :refer :all])
  (:require [jimprint.javafx-helpers :refer :all])
  (import javafx.scene.paint.Color))

(defrecord TextStyle [style-id text-stroke text-fill stroke-height-ratio])

(defn draw-preview [canvas]
  (doto (.getGraphicsContext2D canvas)
    (.setFill Color/BLUE)
    (.fillRect 75 75 100 100)))

(defn init-settings [froot]
  (def cur-style (atom (->TextStyle 1 (Color/rgb 255 0 0) (Color/rgb 0 255 0) 30)))
  (bind-property cur-style [:stroke-height-ratio] (.valueProperty (.lookup froot "#sizeslider")))
  (draw-preview (.lookup froot "#previewcanvas"))
  (doto (.lookup froot "#okbutton")
    (.setOnMouseClicked (handle-action #(println @cur-style)))))

(defn show-settings []
  (show-window "settings.fxml" init-settings))
