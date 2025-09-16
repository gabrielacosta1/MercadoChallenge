package com.mercado_challenge.MercadoAdventure.application.port.in.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetCartQuery {
    private final String userId;
}
