package com.desafio.lanchonete.service;

import com.desafio.lanchonete.exception.DuplicateKeyException;
import com.desafio.lanchonete.mock.LanchoneteData;
import com.desafio.lanchonete.model.Lanchonete;
import com.desafio.lanchonete.repository.LanchoneteRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LanchoneteServiceTest {
    @Mock
    private LanchoneteRepository lanchoneteRepository;

    @InjectMocks
    private LanchoneteService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void givenAnySandwich_whenSavingSandwich_thenSandwichIsSavedAndReturned() throws Exception{
        //given
        Lanchonete lanche = LanchoneteData.getLanchoneteMock();
        when(lanchoneteRepository.insert(eq(lanche))).thenReturn(lanche);

        //when
        Lanchonete lancheRetornado = service.insertLanche(lanche);

        //then
        assertNotNull(lancheRetornado);
        assertEquals(lanche, lancheRetornado);
        verify(lanchoneteRepository, times(1)).insert((eq(lanche)));
    }

    @Test
    public void givenASandwichName_whenSearchingSandwichByName_thenReturnFoundSandwich() throws Exception{
        String name = "teste";

        //given
        Lanchonete lanche = LanchoneteData.getLanchoneteMock().withName(name);
        when(lanchoneteRepository.findByName(eq(name))).thenReturn(Optional.of(lanche));

        //when
        Lanchonete lancheRetornado = service.findByName(name);

        //then
        verify(lanchoneteRepository, times(1)).findByName(name);
        assertEquals(lancheRetornado, lanche);
    }
}
