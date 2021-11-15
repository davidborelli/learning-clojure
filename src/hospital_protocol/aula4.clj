(ns hospital_protocol.aula4
  (:use clojure.pprint))

(defrecord PacienteParticular [id, nome, nascimento, situacao])
(defrecord PacientePlanoDeSaude [id, nome, nascimento, situacao, plano])

; deve assinar pre autorização
; >= 50; plano

(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))

(defn nao-eh-urgente?
  [paciente]
  (not= :urgente (:situacao paciente :normal)))

(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente, procedimento, valor]
    (and (>= valor 50) (nao-eh-urgente? paciente))))

(extend-type PacientePlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente, procedimento, valor]
    (let [plano (:plano paciente)]
      (and (not (some #(= % procedimento) plano)) (nao-eh-urgente? paciente)))))

(let [particular (->PacienteParticular 15, "David", "27/09/1989", :normal)
      plano (->PacientePlanoDeSaude 15, "David B.", "27/09/1989", :normal, [:raio-x, :ultrasom])]
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 500))
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 40))
  (pprint (deve-assinar-pre-autorizacao? plano, :raio-x, 499990))
  (pprint (deve-assinar-pre-autorizacao? plano, :coleta-sangue, 19999)))

(let [particular (->PacienteParticular 15, "David", "27/09/1989", :urgente)
      plano (->PacientePlanoDeSaude 15, "David B.", "27/09/1989", :urgente, [:raio-x, :ultrasom])]
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 500))
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 40))
  (pprint (deve-assinar-pre-autorizacao? plano, :raio-x, 499990))
  (pprint (deve-assinar-pre-autorizacao? plano, :coleta-sangue, 19999)))



; Não se colocar multi no final,
; isso é apenas para poder utilziar o mesmo namespace
(defmulti deve-assinar-pre-autorizacao-multi? class)
(defmethod deve-assinar-pre-autorizacao-multi? PacienteParticular [paciente]
  (println "invocando pciente particular")
  true)

(defmethod deve-assinar-pre-autorizacao-multi? PacientePlanoDeSaude [paciente]
  (println "invocando paciente plano de saude")
  false)

(let [particular (->PacienteParticular 15, "David", "27/09/1989", :urgente)
      plano (->PacientePlanoDeSaude 15, "David B.", "27/09/1989", :urgente, [:raio-x, :ultrasom])]
  (pprint (deve-assinar-pre-autorizacao-multi? particular))
  (pprint (deve-assinar-pre-autorizacao-multi? plano)))


; explorando como funciona a função que define a estratégia de um defmulti
(defn minha-funcao [p]
  (println p)
  (class p))

(defmulti multi-teste minha-funcao)
;(multi-teste "Guilherme")
;(multi-teste :guilherme)

; Não é legal, pois esta misturando keyword e class
; como chave
(defn tipo-de-autorizador [pedido]
  (let [paciente (:paciente pedido)
        situacao (:situacao paciente)
        urgencia? (= :urgente situacao)]
    (if urgencia?
      :sempre-autorizado
      (class paciente ))))

; pedido { :paciente paciente, :valor valor, :procedimento procedimento }
(defmulti deve-assinar-pre-autorizacao-do-pedido? tipo-de-autorizador)

(defmethod deve-assinar-pre-autorizacao-do-pedido? :sempre-autorizado [pedido]
  false)

(defmethod deve-assinar-pre-autorizacao-do-pedido? PacienteParticular [pedido]
  (>= (:valor pedido 0) 50))

(defmethod deve-assinar-pre-autorizacao-do-pedido? PacientePlanoDeSaude [pedido]
  (not (some #(= % (:procedimento pedido)) (:plano (:paciente pedido)))))


(let [particular (->PacienteParticular 15, "David", "27/09/1989", :urgente)
      plano (->PacientePlanoDeSaude 15, "David B.", "27/09/1989", :urgente, [:raio-x, :ultrasom])]
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente particular, :valor 1000, :procedimento :coleta-de-sangue }))
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente plano, :valor 1000, :procedimento :coleta-de-sangue })))

(let [particular (->PacienteParticular 15, "David", "27/09/1989", :normal)
      plano (->PacientePlanoDeSaude 15, "David B.", "27/09/1989", :normal, [:raio-x, :ultrasom])]
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente particular, :valor 1000, :procedimento :coleta-de-sangue }))
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente plano, :valor 1000, :procedimento :coleta-de-sangue })))


















