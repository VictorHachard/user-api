package com.user.mapper;

import com.user.dto.ThemeDto;
import com.user.dto.ThemeSimplifiedDto;
import com.user.mapper.commons.AbstractMapper;
import com.user.model.entities.Theme;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class ThemeMapper extends AbstractMapper<ThemeDto, Theme> {

    @Override
    public List<ThemeDto> getAllDto(List<Theme> aList) {
        List<ThemeDto> res = super.getAllDto(aList);
        Collections.sort(res);
        return res;
    }

    @Override
    public ThemeDto getDto(Theme e) {
        ThemeDto dto = super.getDto(e);
        dto.setName(e.getName());
        dto.setActive(e.isActive());
        dto.setOrder(e.getOrder());
        dto.setImage(this.generateSVG(e));
        dto.setPrimaryColor(e.getPrimaryColor());
        dto.setSecondaryColor(e.getSecondaryColor());
        dto.setTertiaryColor(e.getTertiaryColor());
        dto.setQuaternaryColor(e.getQuaternaryColor());
        dto.setPrimaryTextColor(e.getPrimaryTextColor());
        dto.setSecondaryTextColor(e.getSecondaryTextColor());
        return dto;
    }

    public List<ThemeSimplifiedDto> getAllSimplifiedDto(List<Theme> aList) {
        List<ThemeSimplifiedDto> res = new ArrayList<>();
        aList.forEach(a -> {
            res.add(this.getSimplifiedDto(a));
        });
        Collections.sort(res);
        return res;
    }

    public ThemeSimplifiedDto getSimplifiedDto(Theme e) {
        ThemeSimplifiedDto dto = new ThemeSimplifiedDto();
        dto.setId(e.getId());
        dto.setCreatedAt(e.getCreatedAt());
        dto.setName(e.getName());
        dto.setImage(this.generateSVG(e));
        dto.setPrimaryColor(e.getPrimaryColor());
        dto.setSecondaryColor(e.getSecondaryColor());
        dto.setTertiaryColor(e.getTertiaryColor());
        dto.setQuaternaryColor(e.getQuaternaryColor());
        dto.setPrimaryTextColor(e.getPrimaryTextColor());
        dto.setSecondaryTextColor(e.getSecondaryTextColor());
        return dto;
    }

    private String generateSVG(Theme e) {
        return "<svg width=\"228\" height=\"120\" viewBox=\"0 0 228 120\" fill=\"none\">\n" +
                "<path d=\"M0 0H228V120H0V0Z\" fill=\"" + e.getPrimaryColor() + "\"/>\n" +
                "<rect x=\"29\" y=\"54\" width=\"144\" height=\"53\" rx=\"2\" fill=\"" + e.getSecondaryColor() + "\"/>\n" +
                "<rect x=\"184\" y=\"54\" width=\"32\" height=\"36\" rx=\"2\" fill=\"" + e.getSecondaryColor() + "\"/>\n" +
                "<rect opacity=\"0.3\" x=\"29\" y=\"59\" width=\"144\" height=\"12\" fill=\"#2EA043\"/>\n" +
                "<path opacity=\"0.6\" d=\"M0 0H228V23H0V0Z\" fill=\"" + e.getSecondaryColor() + "\"/>\n" +
                "<rect x=\"13\" y=\"9\" width=\"32\" height=\"6\" rx=\"3\" fill=\"" + e.getTertiaryColor() + "\"/>\n" +
                "<rect x=\"29\" y=\"36\" width=\"48\" height=\"6\" rx=\"3\" fill=\"" + e.getQuaternaryColor() + "\"/>\n" +
                "<rect x=\"34\" y=\"62\" width=\"64\" height=\"6\" rx=\"3\" fill=\"#3FB950\"/>\n" +
                "<rect x=\"210\" y=\"36\" width=\"6\" height=\"6\" rx=\"1\" fill=\"#DA3633\"/>\n" +
                "<rect x=\"202\" y=\"36\" width=\"6\" height=\"6\" rx=\"1\" fill=\"#3FB950\"/>\n" +
                "<rect x=\"53\" y=\"9\" width=\"32\" height=\"6\" rx=\"3\" fill=\"" + e.getTertiaryColor() + "\"/>\n" +
                "<rect x=\"93\" y=\"9\" width=\"32\" height=\"6\" rx=\"3\" fill=\"" + e.getTertiaryColor() + "\"/>\n" +
                "</svg>";
    }

}
