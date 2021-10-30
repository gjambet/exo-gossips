package me.guillaume.recruitment.gossip.message.strategy;

import me.guillaume.recruitment.gossip.domain.Person;

class ProfessorMessageStrategy implements MessageStrategy {

    private String message;
    private String newMessage;
    private boolean isUpdated = false;
    private boolean isSecondTurn = false;

    @Override
    public void initializeMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public boolean updateMessage(String newMessage) {
        if (isSecondTurn) {
            throw new IllegalStateException("Professor can't accept a new message until previous one is propagated.");
        }
        this.newMessage = newMessage;
        isUpdated = true;
        return true;
    }

    @Override
    public void spread(Person listener) {
        if (message == null) {
            return;
        }
        if (isSecondTurn) {
            boolean listenerUpdated = listener.updateMessage(message);
            if (listenerUpdated) {
                message = null;
                isSecondTurn = false;
            }
        } else  {
            isSecondTurn = true;
        }
    }

    @Override
    public void completeSpread() {
        if (isUpdated) {
            message = newMessage;
        }
        newMessage = null;
        isUpdated = false;
    }
}
