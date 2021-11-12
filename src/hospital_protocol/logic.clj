(ns hospital_protocol.logic
  (:require [hospital_protocol.model :as hp.model]))

(defn agora
  []
  (hp.model/to-ms (java.util.Date.)))