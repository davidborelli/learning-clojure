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

(testa-uso-de-pacientes)

