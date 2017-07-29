(ns organizer.views
  (:require [re-frame.core :as re-frame]))


(defn add-event [event]
  (re-frame/dispatch [:add-event event]))

(defn delete-event [id]
  (re-frame/dispatch [:delete-event id]))

(defn render-list [{:keys [id] :as data}]
  [:li {:key id} data [:input {:type "submit" :value "Delete" :on-click #(delete-event id)}]])

(defn options-list [opts on-change]
  [:select {:on-change on-change}
   (for [opt opts]
    [:option {:value opt :key opt} opt])])

(defn second-selection [on-change]
  (options-list (range 60) on-change))

(defn minute-selection [on-change]
  (options-list (range 60) on-change))


(defn hour-selection [on-change]
  (options-list (range 24) on-change))

(defn day-selection [on-change]
  (options-list (range 1 32) on-change))

(defn month-selection [on-change]
  (options-list (range 1 13) on-change))

(defn year-selection [on-change]
  (options-list (range 2017 2026) on-change))


(defn increment-id! [event]
  (let [{:keys [id]} @event]
    (swap! event assoc :id (inc id))))

(defn create-form []
  (let [cur-time {:year  "2017" 
                  :month   "1"
                  :day     "1"
                  :hour    "0"
                  :minute   "0"
                  :second   "0"}
        input (atom {:event "" :time cur-time :id 0})]
   [:div {} 
    "Event:" 
    [:br]
    [:input {:type "text" :name "event" 
             :on-change #(swap! input assoc :event (-> % .-target .-value))}]    
    [:br]
    "Year: "
    [year-selection #(swap! input assoc-in [:time :year] (-> % .-target .-value))]
    "Month: "
    [month-selection #(swap! input assoc-in [:time :month] (-> % .-target .-value))]
    "Day: "
    [day-selection #(swap! input assoc-in [:time :day] (-> % .-target .-value))]
    "Hour: "
    [hour-selection #(swap! input assoc-in [:time :hour] (-> % .-target .-value))]
    "Minute: "
    [minute-selection #(swap! input assoc-in [:time :minute] (-> % .-target .-value))]
    "Second: "
    [second-selection #(swap! input assoc-in [:time :second] (-> % .-target .-value))]
    [:br]
    [:input {:type "submit" :on-click #(do (increment-id! input)
                                           (add-event @input)) }]])
  )


(defn main-panel []
  (let [todo (re-frame/subscribe [:todo])]
    (fn []
      [:div [:ul (for [event @todo] (render-list event))] 
       [create-form]])))
