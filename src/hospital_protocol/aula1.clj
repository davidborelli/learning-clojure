(ns hospital_protocol.aula1
  (:use [clojure pprint]))

(defn adiciona-paciente
  "Os pacientes são um mapa, exemplo {15 { paciente 15 }, 23 { paciente 23 } }"
  [pacientes paciente]
  (if-let [id (get paciente :id)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente não possui id" {:paciente paciente}))))

(defn testa-uso-de-pacientes
  []
  (let [pacientes {}
        guilherme {:id 15, :nome "Guilherme", :nascimento "18/09/81"}
        david {:id 25, :nome "David", :nascimento "27/09/1989"}
        erick {:nome "Erick", :nascimento "28/06/1989"}]
    (pprint (adiciona-paciente pacientes guilherme))
    (pprint (adiciona-paciente pacientes david))
    (pprint (adiciona-paciente pacientes erick))
    ))

;(testa-uso-de-pacientes)

(defrecord Paciente [id, nome, nascimento])

(println (->Paciente 15 "David Borelli" "27/09/1989"))
(pprint (->Paciente 15 "David Borelli" "27/09/1989"))
(pprint (Paciente. 15 "David" "27/10/95"))
(pprint (Paciente. "David" 15 "27/10/95"))
(pprint (Paciente. 15 "27/10/95" "David"))
(pprint (class (Paciente. 15 "27/10/95" "David")))
(pprint (map->Paciente {:id 12, :nome "David", :nascimento "18/09/1989"}))

(let [david (->Paciente 15 "David Borelli" "27/09/1989")]
  (println (get david :id))
  (println (vals david))
  (println (class david))
  (println (record? david))
  (println (.nome david)))

(pprint (map->Paciente {:id 12, :nome "David", :nascimento "18/09/1989" :rg "22222222"}))
;(pprint (Paciente. "David" "27/09/1989"))
(pprint (Paciente. nil "David" "27/09/1989"))
(pprint (map->Paciente {:nome "David", :nascimento "27/09/10", :rg "22222222"}))

(pprint (assoc (Paciente. nil "David" "27/09/1989") :id 38))
(pprint (class (assoc (Paciente. nil "David" "27/09/1989") :id 38)))

;(Thread/sleep)

(pprint (= (->Paciente 15 "David Borelli" "27/09/1989") (->Paciente 15 "David Borelli" "27/09/1989")))
(pprint (= (->Paciente 152 "David Borelli" "27/09/1989") (->Paciente 15 "David Borelli" "27/09/1989")))

