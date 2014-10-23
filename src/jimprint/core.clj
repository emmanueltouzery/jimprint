(ns jimprint.core
  (:require [clojurefx.core :refer :all])
  (:require [jimprint.javafx-helpers :refer :all])
  (:require [jimprint.settings :refer :all])
  (:import [jimprint.settings TextStyle])
  (import javafx.scene.image.Image)
  (import javafx.scene.canvas.Canvas)
  (import javafx.scene.paint.Color)
  (:gen-class))

(defn convert-file [file]
  (def base-image (Image. (.toString (.toURI file))))
  (def canvas (Canvas. (.getWidth base-image) (.getHeight base-image)))
  (.drawImage (.getGraphicsContext2D canvas) base-image 0 0)
  (def text-style (->TextStyle 1 (Color/rgb 255 0 0) (Color/rgb 0 255 0) 30))
  (draw-preview canvas text-style false)
  (def img (.snapshot canvas nil nil))
  (save-image-to-jpeg img "/home/emmanuel/out.jpg")
  (println "OK"))

(defn- files-picked [files]
  ; calculate the output folder then...
  ;(println files)
  (apply convert-file files))

(defn- init-drop-target [froot]
    (def droptarget (.lookup froot "#droptarget"))
    (.setOnDragOver droptarget
      (handle-event #(when (.hasFiles (.getDragboard %))
                      (.acceptTransferModes % javafx.scene.input.TransferMode/COPY_OR_MOVE))))
    (.setOnDragDropped droptarget
                       (handle-event #(files-picked (.getFiles (.getDragboard %))))))

(defn- init-toolbar [froot]
    (def pick-files (.lookup froot "#pickfilesfolders"))
    (println pick-files)
    (.setOnMouseClicked pick-files (handle-action show-settings))
  )

(defn- init-main-window [froot]
  (println froot)
  (run-now ((init-drop-target froot)
            (init-toolbar froot)
            (println "Hello, World!"))))

(defn -main [& args]
  (show-window "jimprint.fxml" init-main-window))
 
 ; (.setScene stage (javafx.scene.Scene. froot 800 600))
