package com.api.v2.cards.services.impl;

import com.api.v2.cards.controller.CardController;
import com.api.v2.cards.domain.CardRepository;
import com.api.v2.cards.dtos.exposed.CardResponseDto;
import com.api.v2.cards.services.interfaces.CardRetrievalService;
import com.api.v2.cards.utils.CardFinder;
import com.api.v2.cards.utils.CardResponseMapper;

import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static de.kamillionlabs.hateoflux.linkbuilder.SpringControllerLinkBuilder.linkTo;

@Service
public class CardRetrievalServiceImpl implements CardRetrievalService {

    private final CardFinder cardFinder;
    private final CardRepository cardRepository;

    public CardRetrievalServiceImpl(
            CardFinder cardFinder,
            CardRepository cardRepository
    ) {
        this.cardFinder = cardFinder;
        this.cardRepository = cardRepository;
    }

    @Override
    public Flux<HalResourceWrapper<CardResponseDto, Void>> findAll() {
        return cardRepository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .flatMap(CardResponseMapper::mapToMono)
                .map(dto -> {
                    return HalResourceWrapper
                            .wrap(dto)
                            .withLinks(
                                    linkTo(
                                            CardController.class,
                                            CardController::findAll
                                    ).withSelfRel(),
                                    linkTo(
                                          CardController.class,
                                          controller -> controller.findById(dto.id())
                                    ).withRel("find card by id"),
                                    linkTo(
                                            CardController.class,
                                            controller -> controller.delete(dto.id())
                                    ).withRel("delete card by id")
                            );
                });
    }

    @Override
    public Mono<HalResourceWrapper<CardResponseDto, Void>> findById(String id) {
        return cardFinder
                .find(id)
                .flatMap(CardResponseMapper::mapToMono)
                .map(dto -> {
                    return HalResourceWrapper
                            .wrap(dto)
                            .withLinks(
                                    linkTo(
                                            CardController.class,
                                            controller -> controller.findById(dto.id())
                                    ).withSelfRel(),
                                    linkTo(
                                            CardController.class,
                                            controller -> controller.delete(dto.id())
                                    ).withRel("delete card by id")
                            );
                });
    }
}
