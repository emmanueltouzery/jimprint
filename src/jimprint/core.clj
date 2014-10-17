(ns jimprint.core
  (:require [clojurefx.core :refer :all])
  (:gen-class))

(defn files-picked [files]
  (println files)
  )

(defn handleEvent [handler]
  (reify javafx.event.EventHandler
    (handle [this e] (handler e))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (def froot (run-now (javafx.fxml.FXMLLoader/load (java.net.URL. "file:///home/emmanuel/home/jimprint/jimprint.fxml"))))
 ; (clojure.java.io/resource "<FILENAME>.fxml")))))))
  (def stage (run-now
  (doto
  (.build (javafx.stage.StageBuilder/create))
  (.setScene (javafx.scene.Scene. froot)))))
 
 ; (.setScene stage (javafx.scene.Scene. froot 800 600))
  (println froot)
  (run-now (.show stage))
  (run-now (
            (def droptarget (.lookup froot "#droptarget"))
            (println droptarget)
            (.setOnDragOver droptarget
                              (handleEvent #(when (.hasFiles (.getDragboard %))
                                                 (.acceptTransferModes % javafx.scene.input.TransferMode/COPY_OR_MOVE))))
            (.setOnDragDropped droptarget
                               (handleEvent #(files-picked (.getFiles (.getDragboard %)))))))
  (println "Hello, World!"))
