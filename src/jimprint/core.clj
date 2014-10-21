(ns jimprint.core
  (:require [clojurefx.core :refer :all])
  (:require [jimprint.javafx-helpers :refer :all])
  (:require [jimprint.settings :refer :all])
  (:gen-class))

(defn files-picked [files]
  (println files)
  )

(defn init-drop-target [froot]
    (def droptarget (.lookup froot "#droptarget"))
    (.setOnDragOver droptarget
      (handle-event #(when (.hasFiles (.getDragboard %))
                      (.acceptTransferModes % javafx.scene.input.TransferMode/COPY_OR_MOVE))))
    (.setOnDragDropped droptarget
                       (handle-event #(files-picked (.getFiles (.getDragboard %))))))

(defn init-toolbar [froot]
    (def pick-files (.lookup froot "#pickfilesfolders"))
    (println pick-files)
    (.setOnMouseClicked pick-files
                        (handle-action #(show-settings)))
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
