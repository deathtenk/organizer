(ns organizer.events
  (:require [re-frame.core :as re-frame]
            [organizer.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
  :add-event
  (fn [{:keys [todo] :as db} [_ event]] 
    (let [m-todo (conj todo event)
          validation (re-frame/subscribe [:validation])]
     (if @validation
      (assoc db :todo m-todo)
      db))))

(defn input-valid? [{:keys [event start-time end-time] :as input}]
  (cond 
    (empty? event) nil
    (empty? start-time) nil
    (empty? end-time) nil
    :else input))

(re-frame/reg-event-db
  :validation-event
  (fn [{:keys [validation] :as db} [_ event]]
    (if (input-valid? event)
      (assoc db :validation true)
      (assoc db :validation false)
      )))

(re-frame/reg-event-db
  :delete-event
  (fn [{:keys [todo] :as db} [_ id]]
    (let [m-todo (remove #(= (:id %) id) todo)]
      (assoc db :todo m-todo))))
