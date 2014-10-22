(ns jimprint.core
  (:require [clojurefx.core :refer :all])
  (:require [jimprint.javafx-helpers :refer :all])
  (:require [jimprint.settings :refer :all])
  (import javafx.scene.image.Image)
  (import javafx.embed.swing.SwingFXUtils)
  (import java.io.File)
  (import javax.imageio.ImageIO)
  (:gen-class))

(defn convert-file [file]
  (def img (Image. (.toString (.toURI file))))
  (def outputFile (File. "/home/emmanuel/out.jpg"))
  (ImageIO/write (SwingFXUtils/fromFXImage img nil) "jpeg" outputFile)
  (println "OK"))

(defn files-picked [files]
  ; calculate the output folder then...
  ;(println files)
  (apply convert-file files))

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
    (.setOnMouseClicked pick-files (handle-action show-settings))
  )

(defn init-main-window [froot]
  (println froot)
  (run-now ((init-drop-target froot)
            (init-toolbar froot)
            (println "Hello, World!"))))

(defn -main [& args]
  (show-window "jimprint.fxml" init-main-window))
 
 ; (.setScene stage (javafx.scene.Scene. froot 800 600))
