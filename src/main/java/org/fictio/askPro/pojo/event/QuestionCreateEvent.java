package org.fictio.askPro.pojo.event;

import org.fictio.askPro.pojo.Question;
import org.springframework.context.ApplicationEvent;

public class QuestionCreateEvent extends ApplicationEvent {
	private static final long serialVersionUID = -3336062118835049127L;
	public QuestionCreateEvent(Question question) {
		super(question);
	}
}
