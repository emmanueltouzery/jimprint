(ns jimprint.settings
  (:require [clojurefx.core :refer :all])
  (:require [jimprint.javafx-helpers :refer :all])
  (import javafx.scene.paint.Color)
  (import javafx.scene.text.Font))

(defrecord TextStyle [style-id text-stroke text-fill stroke-height-ratio])

(defn change-font-size [gc size]
    (.setFont gc (Font/font (.getFamily (.getFont gc)) size)))

(defn draw-preview [canvas cur-style]
  (doto (.getGraphicsContext2D canvas)
    (change-font-size (:stroke-height-ratio @cur-style))
    (.setFill Color/BLUE)
    (.fillText "test" 50 50)))

(defn init-settings [froot]
  (def cur-style (atom (->TextStyle 1 (Color/rgb 255 0 0) (Color/rgb 0 255 0) 30)))
  (def refresh-canvas
    (fn [& _] (draw-preview (.lookup froot "#previewcanvas") cur-style)))
  (refresh-canvas)
  (add-watch cur-style nil refresh-canvas)
  (bind-property cur-style [:stroke-height-ratio] (.valueProperty (.lookup froot "#sizeslider")))
  (doto (.lookup froot "#okbutton")
    (.setOnMouseClicked (handle-action #(println @cur-style)))))

(defn show-settings []
  (show-window "settings.fxml" init-settings))
