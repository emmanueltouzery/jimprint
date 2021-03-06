(ns jimprint.settings
  (:require [clojurefx.core :refer :all])
  (:require [jimprint.javafx-helpers :refer :all])
  (:require [jimprint.styleedit :refer :all])
  (import javafx.scene.paint.Color))

(defrecord TextStyle [style-id text-stroke text-fill stroke-height-ratio])

(defn draw-preview [canvas cur-style clear]
  (doto (.getGraphicsContext2D canvas)
    (#(when clear (clear-gc % canvas)))
    (change-font-size (:stroke-height-ratio cur-style))
    (.setFill Color/BLUE)
    (.fillText "test" 50 50)))

(defn- init-settings [froot]
  (def cur-style (atom (->TextStyle 1 (Color/rgb 255 0 0) (Color/rgb 0 255 0) 30)))
  (def refresh-canvas
    (fn [& _] (draw-preview (.lookup froot "#previewcanvas") @cur-style true)))
  (refresh-canvas)
  (add-watch cur-style nil refresh-canvas)
  (bind-property cur-style [:stroke-height-ratio] (.valueProperty (.lookup froot "#sizeslider")))
  (on-click froot "#styleedit" #(show-window "styleedit.fxml" init-styleedit))
  (on-click froot "#okbutton" #(println @cur-style)))

(defn show-settings []
  (show-window "settings.fxml" init-settings))
