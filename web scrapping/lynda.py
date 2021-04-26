from selenium import webdriver
import requests
from PIL import Image
import time
import io
import os
import random

base_url = "https://www.linkedin.com"
start_url = "https://www.linkedin.com/learning/topics/human-resources-3?trk=library_subjects_link&upsellOrderOrigin=default_guest_learning"

driver = webdriver.Firefox('E:\geckodriver')

#users = ['Bear Cahill', 'Robert Starmer', 'Jose Miguel Rady Allendel', 'Tiago Costa', 'Lynn Langit', 'Walt Ritscher', 'Jesse Liberty', 'Chris Bailey', 'Adrienne Braganza Tacke', 'Chander Dhall', 'Frank P Molley III', 'Duard Lynn Davis', 'Brien Posey', 'David Linthicum', 'Arthur Ulfeldy', 'Sahil Malik', 'Matt Milner', 'Xian Ke', 'Jeff Winesett', 'Sharon Bennett', 'Brian Culp', 'Kunal D Mehta', 'Joseph Holbrook', 'David Elfassy', 'Julio Appling, Andrew Bettany', 'TechSnips, LLC', 'Bear Cahill', 'Robert Starmer', 'Jose Miguel Rady Allendel', 'Tiago Costa', 'Lynn Langit', 'Walt Ritscher', 'Jesse Liberty', 'Chris Bailey', 'Adrienne Braganza Tacke', 'Chander Dhall', 'Frank P Molley III', 'Duard Lynn Davis', 'Brien Posey', 'David Linthicum', 'Arthur Ulfeldy', 'Sahil Malik', 'Matt Milner', 'Xian Ke', 'Jeff Winesett', 'Sharon Bennett', 'Brian Culp', 'Kunal D Mehta', 'Joseph Holbrook', 'David Elfassy', 'Julio Appling, Andrew Bettany', 'TechSnips, LLC', 'David Linthicum', 'Mike Chapple', 'Kevin L. Jackson', 'David Elfassy', 'Lynn Langit', 'Sharif Nijim', 'Martynas Valkunas', 'Jeff Winesett', 'Sharon Bennett', 'Tom Carpenter', 'Brian Culp']
nama_ins = []
idcourse = 58
idinstructor = 104
def start(url,id_course, ke, id_instructor, nama_ins):
    
    
    topic = 'Database Management'
    driver.get(url)
    print(url)
    
    list_subtopic = driver.find_element_by_class_name('topics-pills__list')
    subtopic = list_subtopic.find_elements_by_tag_name('li')
    for link_subtopic in subtopic:
        if(ke >= 0):
            sub = link_subtopic.find_element_by_tag_name('a')
            nama_sub = sub.text.strip()
            print(nama_sub)
            link = sub.get_attribute('href')
            driver_sub_topic = webdriver.Firefox('E:\geckodriver')
            driver_sub_topic.get(link)
            list_course = driver_sub_topic.find_element_by_class_name('topics-search-results__course-list')
            collections = list_course.find_elements_by_tag_name('a')
            print('jalan 2')
            ke = 0
            for i in collections:
            
                id_course += 1
                masuk_ke_list_course = i.get_attribute('href')
                driver2 = webdriver.Firefox('E:\geckodriver')
                driver2.get(masuk_ke_list_course)
                print('jalan 3')
                error = 0
                try:
                    header_video = driver2.find_element_by_class_name('content__hero__video')
                    thumbnail = header_video.find_element_by_tag_name('img').get_attribute('src')
                    judul = driver2.find_element_by_class_name('content__header-headline').text.strip()
                    try:
                        pic= requests.get(thumbnail, timeout=100)
                    except requests.exceptions.ConnectionError:
                        print ('exception')

                    string = './thumbnailbaru/'+str(id_course)+'.jpg'
                    fp = open(string,'wb')
                    fp.write(pic.content)
                    fp.close()
                except:
                    print('video tidak tersedia')
                    error = 1
                if(error == 0):
                    header_url_detail = driver2.find_element_by_class_name('content')
                    header_url_detail = header_url_detail.find_element_by_class_name('content__hero')       ##############
                    url_detail_course = header_url_detail.find_element_by_tag_name('a').get_attribute('href')
                    driver_detail_course = webdriver.Firefox('E:\geckodriver')
                    driver_detail_course.get(url_detail_course)
                
                    video = driver_detail_course.find_element_by_class_name('share-native-video').get_attribute('innerHTML')
                    splitt = video.split('&quot')
                    print(video)
                    download_url = splitt[3].replace(';','')
                    download = requests.get(download_url, stream=True)
                    with open(str(id_course)+'b.mp4', "wb") as f:
                        for chunk in download.iter_content(chunk_size=2048):
                            f.write(chunk)
                    
                    f.close()
                    header_nama_instructor = driver_detail_course.find_element_by_class_name('overview-panel__entity-lockup-title')
                    instructor = header_nama_instructor.find_element_by_tag_name('a')
                    nama_instructor = instructor.text.strip()
                    id_foto = 0
                    nama_kembar = 0
                    for namainstructor in nama_ins:
                        if(nama_instructor.lower() == namainstructor.lower()):
                            nama_kembar = 1;
                            
                    
                    header_deskripsi = driver_detail_course.find_element_by_class_name('overview-panel__section-wrapper')
                    deskripsi = header_deskripsi.find_element_by_class_name('panel__description').text
                    print(deskripsi)
                    
                    viewer = driver_detail_course.find_element_by_class_name('overview-panel__social-headline').text.strip()
                    viewer = viewer.split(' ')[0]
                    print(judul)
                    print(viewer)
                    print(nama_instructor)
                    menuju_instructor = instructor.get_attribute('href')
                    driver_profile = webdriver.Firefox('E:\geckodriver')
                    driver_profile.get(menuju_instructor)
                    header_foto = driver_profile.find_element_by_class_name('author-profile__profile-image')
                    foto = header_foto.get_attribute('src')
                    try:
                        pic= requests.get(foto, timeout=100)
                    except requests.exceptions.ConnectionError:
                        print ('exception')
                    string = './foto_user/'+str(id_instructor)+'.jpg'
                    fp = open(string,'wb')
                    fp.write(pic.content)
                    fp.close()
                    
                    deskripsi_instructor = driver_profile.find_element_by_class_name('author-profile__biography').text.strip()
                    jabatan = "-"
                    try:
                        jabatan = driver_profile.find_element_by_class_name('author-profile__headline').text.strip()
                    except:
                        print('tidak punya jabatan')
                    print("deskripsi instructor: "+deskripsi_instructor)
                    try:
                        print("jabatan :"+jabatan)
                    except:
                        error = 1
                        id_course -= 1
                        print('error jabatan')
                    viewer = int(viewer.replace(',',''))/100
                    like = random.randint(0, int(int(viewer)/10))
                    if(error == 0):
                        f_dataset = open("datasetbaru.txt", "a", encoding="utf-8")
                        f_dataset.write(str(id_course)+'@@@'+topic+'@@@'+nama_sub+'@@@'+judul+'@@@'+str(deskripsi)+'@@@'+nama_instructor+'@@@'+str(viewer)+'@@@'+str(like)+'@@@'+"###")
                        f_dataset.close()

                        
                        if(nama_kembar == 0):
                            f_ins = open("instructorbaru.txt", "a", encoding="utf-8")
                            f_ins.write(str(id_instructor)+'@@@'+nama_instructor+'@@@'+str(deskripsi_instructor)+'@@@'+jabatan+"###")
                            f_ins.close()
                            nama_ins.append(nama_instructor)
                            id_instructor += 1
                    driver_profile.quit()
            
                    driver_detail_course.quit()
                driver2.quit()
            driver_sub_topic.quit()
        ke += 1
    return id_course, id_instructor, nama_ins
        
        

idcourse, idinstructor, nama_ins = start(start_url, idcourse, 0, idinstructor, nama_ins)
