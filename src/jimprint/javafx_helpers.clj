(ns jimprint.javafx-helpers
  (:require [clojurefx.core :refer :all]))

(defn handle-event [handler]
  (reify javafx.event.EventHandler
    (handle [this e] (handler e))))

(defn show-window [fxml-path callback]
  (def froot (run-now (javafx.fxml.FXMLLoader/load (java.net.URL. fxml-path))))
 ; (clojure.java.io/resource "<FILENAME>.fxml")))))))
  (def stage (run-now
  (doto
  (.build (javafx.stage.StageBuilder/create))
  (.setScene (javafx.scene.Scene. froot)))))
  (run-now (.show stage))
  (callback froot))
