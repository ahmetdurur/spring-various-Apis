package com.haydikodlayalim.service.impl;

import com.haydikodlayalim.dto.KisiDto;
import com.haydikodlayalim.entity.Adres;
import com.haydikodlayalim.entity.Kisi;
import com.haydikodlayalim.repo.AdresRepository;
import com.haydikodlayalim.repo.KisiRepository;
import com.haydikodlayalim.service.KisiService;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KisiServiceImpl  implements KisiService {

    private final KisiRepository kisiRepository;
    private final AdresRepository adresRepository;

    @Override
    @Transactional
    public KisiDto save(KisiDto kisiDto) {
       // Assert.isNull(kisiDto.getAdi(), "Adı Alanı Boş Olamaz");

        Kisi kisi = new Kisi();
        kisi.setAdi(kisiDto.getAdi());
        kisi.setSoyadi(kisiDto.getSoyadi());
        final Kisi kisiDb = kisiRepository.save(kisi);

        List<Adres> adresList = new ArrayList<>();
        kisiDto.getAdresleri().forEach(item -> {
            Adres adres = new Adres();
            adres.setAdres(item);
            adres.setAdresTip(Adres.AdresTip.DIGER);
            adres.setAktif(Boolean.TRUE);
            adres.setKisi(kisiDb);
            adresList.add(adres);
        } ) ;

        adresRepository.saveAll(adresList);
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<KisiDto> getAll() {
        List<Kisi> kisiler = kisiRepository.findAll();
        List<KisiDto> kisiDtos = new ArrayList<>();
        kisiler.forEach(it -> {
            KisiDto dto = new KisiDto();
            dto.setId(it.getId());
            dto.setAdi(it.getAdi());
            dto.setSoyadi(it.getSoyadi());
            dto.setAdresleri(it.getAdresleri().stream().map(Adres::getAdres)
                    .collect(Collectors.toList()));
            kisiDtos.add(dto);
        });
        return kisiDtos;
    }

    @Override
    public Page<List<KisiDto>> getAll(Pageable pageable) {
        return null;
    }
}
