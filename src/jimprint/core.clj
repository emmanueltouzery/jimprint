(ns jimprint.core
  (:require [clojurefx.core :refer :all])
  (:gen-class))

(defn files-picked [files]
  (println files)
  )

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

(defn init-drop-target [froot]
    (def droptarget (.lookup froot "#droptarget"))
    (.setOnDragOver droptarget
      (handle-event #(when (.hasFiles (.getDragboard %))
                      (.acceptTransferModes % javafx.scene.input.TransferMode/COPY_OR_MOVE))))
    (.setOnDragDropped droptarget
                       (handle-event #(files-picked (.getFiles (.getDragboard %))))))

(defn init-settings [froot]
  (println froot))

(defn init-toolbar [froot]
    (def pick-files (.lookup froot "#pickfilesfolders"))
    (println pick-files)
    (.setOnMouseClicked pick-files
                        (handle-event (fn [_] (show-window
                                                "file:///home/emmanuel/home/jimprint/settings.fxml" init-settings))))
  )

(defn init-main-window [froot]
  (println froot)
  (run-now ((init-drop-target froot)
            (init-toolbar froot)
            (println "Hello, World!"))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (show-window "file:///home/emmanuel/home/jimprint/jimprint.fxml" init-main-window))
 
 ; (.setScene stage (javafx.scene.Scene. froot 800 600))
