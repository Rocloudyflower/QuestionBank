
from aip import AipOcr
import sys
def get_file_content(filePath):
    with open(filePath, 'rb') as fp:
        return fp.read()
def get_words(image_path):
    """ 你的 APPID AK SK """
    APP_ID = '17712908'
    API_KEY = '3TvcTfSPey2VkwaGt3bOeW72'
    SECRET_KEY = 'TjhPUD6ffZK5PIGxTZZVHuFjBVbeHtqo'
    client = AipOcr(APP_ID, API_KEY, SECRET_KEY)
    image=get_file_content(image_path)
    ret=client.basicGeneral(image)
    for item in ret['words_result']:
        print(item['words'])

def transGbk2Unicode(str_v):
    str_s = str_v.replace(r'\x', r'%')
    res = eval(repr(str_s).replace('\\', '\\\\'))
    return res.decode('gb2312')

if __name__=='__main__':
    img_path = transGbk2Unicode(sys.argv[1])
    get_words(img_path)
