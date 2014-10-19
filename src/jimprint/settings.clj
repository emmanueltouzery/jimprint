(ns jimprint.settings
  (:require [clojurefx.core :refer :all])
  (:require [jimprint.javafx-helpers :refer :all]))

(defn init-settings [froot]
  (println froot))

(defn show-settings []
  (show-window
    "file:///home/emmanuel/home/jimprint/settings.fxml" init-settings))
