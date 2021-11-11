(ns hospital_protocol.aula2
  (:use clojure.pprint))

(defrecord PacienteParticular [id, nome, nascimento])
(defrecord PacientePlanoDeSaude [id, nome, nascimento, plano])

(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))

(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente, procedimento, valor]
    (>= valor 50)))

(extend-type PacientePlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente, procedimento, valor]
    (let [plano (:plano paciente)]
      (not (some #(= % procedimento) plano)))))

; Alternativa seria implementar diretamente
;(defrecord PacientePlanoDeSaude
;  [id, nome, nascimento, plano]
;  Cobravel
;  (deve-assinar-pre-autorizacao? [paciente, procedimento, valor]
;    (let [plano (:plano paciente)]
;      (not (some #(= % procedimento) plano)))))


(let [particular (->PacienteParticular 15, "David", "27/09/1989")
      plano (->PacientePlanoDeSaude 15, "David B.", "27/09/1989", [:raio-x, :ultrasom])]
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 500))
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 40))
  (pprint (deve-assinar-pre-autorizacao? plano, :coleta-sangue, 19999)))
