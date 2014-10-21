(ns jimprint.settings
  (:require [clojurefx.core :refer :all])
  (:require [jimprint.javafx-helpers :refer :all])
  (import javafx.scene.paint.Color))

(defrecord TextStyle [style-id text-stroke text-fill stroke-height-ratio])

(defn init-settings [froot]
  (def cur-style (atom (->TextStyle 1 (Color/rgb 255 0 0) (Color/rgb 0 255 0) 0.5)))
  (bind-property cur-style [:stroke-height-ratio] (.valueProperty (.lookup froot "#sizeslider")))
  (doto (.lookup froot "#okbutton")
    (.setOnMouseClicked (handle-action #(println @cur-style)))))

(defn show-settings []
  (show-window
    "file:///home/emmanuel/home/jimprint/settings.fxml" init-settings))
