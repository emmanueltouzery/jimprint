(ns jimprint.javafx-helpers
  (:require [clojurefx.core :refer :all])
  (import javafx.scene.text.Font)
  (import javafx.embed.swing.SwingFXUtils)
  (import java.awt.image.BufferedImage)
  (import java.io.File)
  (import javax.imageio.ImageIO))

(defn handle-event [handler]
  (reify javafx.event.EventHandler
    (handle [this e] (handler e))))

; use handle-action if you don't care about the event.
(defn handle-action [handler]
  (reify javafx.event.EventHandler
    (handle [this e] (handler))))

(defn on-click [froot sel handler]
  (doto (.lookup froot sel)
    (.setOnMouseClicked (handle-action handler))))

(defn show-window [fxml-path callback]
  (def froot (run-now (javafx.fxml.FXMLLoader/load (clojure.java.io/resource fxml-path))))
  (def stage (run-now 
               (doto (.build (javafx.stage.StageBuilder/create))
                 (.setScene (javafx.scene.Scene. froot)))))
  (run-now (.show stage))
  (callback froot))

; set the value of the property to the value of the atom,
; then update the atom from the property.
; TODO need to remove the listener at some point?
(defn bind-property [model keylist property]
  (.setValue property (get-in @model keylist))
  (.addListener property (reify javafx.beans.value.ChangeListener
                           (changed [this obs old new]
                             (swap! model (fn [m] (assoc-in m keylist new)))))))

; canvas helpers

(defn change-font-size [gc size]
    (.setFont gc (Font/font (.getFamily (.getFont gc)) size)))

(defn clear-gc [gc canvas]
  (.clearRect gc 0 0 (.getWidth canvas) (.getHeight canvas)))

 ; sadly need this... http://stackoverflow.com/a/19605733/516188
(defn save-image-to-jpeg [img filename]
  (def bufimg (SwingFXUtils/fromFXImage img nil))
  (def img-rgb (BufferedImage. (.getWidth img) (.getHeight img) BufferedImage/OPAQUE))
  (def graphics (.createGraphics img-rgb))
  (.drawImage graphics bufimg 0 0 nil)
  (def outputFile (File. filename))
  (ImageIO/write img-rgb "jpeg" outputFile)
  (.dispose graphics))
