(ns jimprint.core
  (:require [clojurefx.core :refer :all])
  (:gen-class))

(defn files-picked [files]
  (println files)
  )

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
                            (reify javafx.event.EventHandler
                              (handle [this e] (when (.hasFiles (.getDragboard e))
                                                 (.acceptTransferModes e javafx.scene.input.TransferMode/COPY_OR_MOVE)))))
            (.setOnDragDropped droptarget
                             (reify javafx.event.EventHandler
                               (handle [this e] (files-picked (.getFiles (.getDragboard e))))))))
  (println "Hello, World!"))
