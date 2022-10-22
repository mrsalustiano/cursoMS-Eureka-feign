package br.mrsalustiano.mscartoes.service.queues;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;



@Component
public class EmissaoCartaoSubscriber {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmissaoCartaoSubscriber.class);
	
	@RabbitListener(queues = "${mq.queues.emissao-cartoes}")
	public void receberSolicitacaoEmissaoCartao(@Payload String payload) {
		LOGGER.info(payload);
		
		
	}

}
