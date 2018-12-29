package com.jx;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<List<String>> listList = new ArrayList<List<String>>();
        List<Province> provinceList = new ArrayList<Province>();
        File directory = new File("");// ����Ϊ��
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(courseFile);
        //ÿһ�ж���һ��String
        List<String> strings = FileUtils.readFile(courseFile+"/province_201810.txt");
        for (int i = 0; i < strings.size(); i++) {
            //每一行根据空格分割，便于取出有用的�??
            List<String> list = Arrays.asList(strings.get(i).trim().split(" "));
            listList.add(list);
        }
        for (int i = 0; i < listList.size(); i++) {
            if (listList.get(i).size() < 2) {
                continue;
            }
            String provinceName = listList.get(i).get(1);
            String provinceCode = listList.get(i).get(0);
            //������ȡʡ����λ
            if (provinceCode.endsWith("0000")) {
                Province province = new Province();
                provinceList.add(province);
                province.setCode(provinceCode);
                province.setName(provinceName);
                List<City> cities = new ArrayList<City>();
                province.setCityList(cities);
                //��ۣ����ţ�̨�壬û���м�������λ���֣����� ���� ��ʡ�ݱ���һ��
                if (provinceName.contains("���")||provinceName.contains("����")||provinceName.contains("̨��")){
                    City city = new City();
                    List<Area> areas = new ArrayList<Area>();
                    city.setName(provinceName);
                    city.setCode(provinceCode);
                    city.setAreaList(areas);
                    cities.add(city);
                    Area area = new Area();
                    area.setName(provinceName);
                    area.setCode(provinceCode);
                    areas.add(area);
                }
                //ֱϽ�� ���к�ʡ������һ��
                if (provinceName.contains("����")||provinceName.contains("�Ϻ�")||provinceName.contains("���")||provinceName.contains("����")){
                    City city = new City();
                    List<Area> areas = new ArrayList<Area>();
                    city.setName(provinceName);
                    city.setCode(provinceCode);
                    city.setAreaList(areas);
                    cities.add(city);
                    //����
                    for (int k = 0; k < listList.size(); k++) {
                        if (listList.get(k).size() < 3) {
                            continue;
                        }
                        String areaName = listList.get(k).get(2);
                        String areaCode = listList.get(k).get(1);
                        if (!provinceCode.equals(areaCode) && areaCode.startsWith(provinceCode.substring(0, 2))) {
                            Area area = new Area();
                            area.setName(areaName);
                            area.setCode(areaCode);
                            areas.add(area);
                        }
                    }
                }
                for (int j = 0; j < listList.size(); j++) {
                    if (listList.get(j).size() < 3) {
                        continue;
                    }
                    String cityName = listList.get(j).get(2);
                    String cityCode = listList.get(j).get(1);
                    //������ȡ�ؼ���
                    if (!cityCode.equals(provinceCode) && cityCode.startsWith(provinceCode.substring(0, 2)) && cityCode.endsWith("00")) {
                        City city = new City();
                        List<Area> areas = new ArrayList<Area>();
                        city.setName(cityName);
                        city.setCode(cityCode);
                        city.setAreaList(areas);
                        cities.add(city);
                        //������ȡ����
                        for (int k = 0; k < listList.size(); k++) {
                            if (listList.get(k).size() < 3) {
                                continue;
                            }
                            String areaName = listList.get(k).get(2);
                            String areaCode = listList.get(k).get(1);
                            if (!areaCode.equals(cityCode) && areaCode.startsWith(cityCode.substring(0, 4))) {
                                Area area = new Area();
                                area.setName(areaName);
                                area.setCode(areaCode);
                                areas.add(area);
                            }
                        }
                    }
                }
            }
        }
        //ת����JSON����
        String jsonStrings = new Gson().toJson(provinceList);
        //д���ļ�
        FileUtils.createJsonFile(jsonStrings, courseFile+"/province.json");
    }

}
