package br.com.itau.ensurancequote.gateways;

import br.com.itau.ensurancequote.gateways.repositories.entities.QuoteEntity;
import br.com.itau.ensurancequote.gateways.repositories.entities.QuoteEntityMapper;
import br.com.itau.ensurancequote.gateways.repositories.QuoteRepository;
import br.com.itau.ensurancequote.domains.models.Quote;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class QuoteGateway {
    private final QuoteRepository quoteRepository;
    private final QuoteEntityMapper quoteEntityMapper;

    public QuoteGateway(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
        this.quoteEntityMapper = new QuoteEntityMapper();
    }

    @Transactional
    public Quote save(Quote quote) {
        final QuoteEntity entity = quoteEntityMapper.toEntity(quote);
        final QuoteEntity saved = quoteRepository.save(entity);
        return quoteEntityMapper.toDomain(saved);
    }

    @Transactional(readOnly = true)
    public Optional<Quote> findById(Long id) {
        return quoteRepository.findById(id).map(quoteEntityMapper::toDomain);
    }
}
