package im.educ.groovy.service

import im.educ.groovy.model.Hello

@Singleton
class HelloService {

    List<Hello> hellos

    Hello addMsg(String msg) {
        Hello hello = new Hello(msg)
        hellos.add(hello) as Hello
    }

//
//    /**
//     * Finds all to-do's for the given user
//     */
    List<Hello> findAll() {
        return hellos
    }
//
//    /**
//     * Publishes an event for the given username
//     */
//    public void publishEvent(String username, TodoEvent todoEvent) {
//        try {
//            getEvents(username).put(todoEvent);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Finds all the un-consumed events for the given username
//     */
//    public BlockingQueue<TodoEvent> getEvents(String username) {
//        BlockingQueue<TodoEvent> userEvents = this.events.get(username);
//        if (userEvents == null) {
//            userEvents = new LinkedBlockingQueue<>();
//            this.events.put(username, userEvents);
//        }
//        return userEvents;
//    }


}
