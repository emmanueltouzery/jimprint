(ns jimprint.javafx-helpers
  (:require [clojurefx.core :refer :all]))

(defn handle-event [handler]
  (reify javafx.event.EventHandler
    (handle [this e] (handler e))))

; use handle-action if you don't care about the event.
(defn handle-action [handler]
  (reify javafx.event.EventHandler
    (handle [this e] (handler))))

(defn show-window [fxml-path callback]
  (def froot (run-now (javafx.fxml.FXMLLoader/load (java.net.URL. fxml-path))))
 ; (clojure.java.io/resource "<FILENAME>.fxml")))))))
  (def stage (run-now
  (doto
  (.build (javafx.stage.StageBuilder/create))
  (.setScene (javafx.scene.Scene. froot)))))
  (run-now (.show stage))
  (callback froot))

(defn bind-property [model keylist property]
  (.addListener property (reify javafx.beans.value.ChangeListener
                           (changed [this obs old new]
                             (swap! model (fn [m] (assoc-in m keylist new)))))))
