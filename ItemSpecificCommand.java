import java.util.Hashtable;

class ItemSpecificCommand extends Command {

    private String verb;
    private String noun;
                        

    ItemSpecificCommand(String verb, String noun) {
        this.verb = verb;
        this.noun = noun;
    }

    /*If an action committed by the adventurer returns a message that is equivalent 
      to one of the names of the events that can occur, then this execute method
      will return the string produced from the specific item event that was called
      on the item object. The item events are detailed in the Item class.
    */
    public String execute() {
        
        Item itemReferredTo = null;
        try {
            itemReferredTo = GameState.instance().getItemInVicinityNamed(noun);
        } catch (Item.NoItemException e) {
            return "There's no " + noun + " here.";
        }
        
        String msg = itemReferredTo.getMessageForVerb(verb);
        String[] events = itemReferredTo.getEventsForVerb(verb);
        if(events != null) {
            for(String event : events) {
                switch (event.substring(0, event.indexOf('('))) {
                    case "Transform":
                        msg += "\n" + itemReferredTo.transform(event.substring(event.indexOf("(") + 1, event.length() - 1));
                        break;
                    case "Wound":
                        msg += "\n" + itemReferredTo.wound(Integer.parseInt(event.substring(event.indexOf("(") + 1, event.length() - 1)));
                        break;
                    case "Teleport":
                        msg += "\n" + itemReferredTo.teleport(event.substring(event.indexOf("(") + 1, event.length() - 1));
                        break;
                    case "Disappear":
                        msg += "\n" + itemReferredTo.disappear();
                        break;
                }
            }
        }
        return (msg == null ? 
            "Sorry, you can't " + verb + " the " + noun + "." : msg) + "\n";
    }
}
