import requests
import os

# 科室列表
departments = [
    "内科",
    "外科",
    "妇产科",
    "儿科",
    "眼科",
    "耳鼻喉科",
    "口腔科",
    "皮肤科"
]

# 下载目录
download_dir = "d:\\python\\Hospital2.0\\src\\main\\webapp\\images"

# 确保目录存在
os.makedirs(download_dir, exist_ok=True)

# 为每个科室下载图片
def download_department_image(dept_name, index):
    # 使用bing图片搜索API获取图片
    # 注意：这里使用的是公开的图片搜索API，实际项目中应使用更可靠的图片源
    search_query = f"医院{dept_name} 科室图片 高清"
    
    # 这里使用requests库获取图片，实际可以替换为更合适的图片源
    # 由于直接调用第三方API可能有问题，这里使用示例图片URL
    # 实际应用中，建议从可靠的图片库或医院官网获取图片
    
    # 示例图片URL模板（使用picsum.photos服务生成随机图片）
    image_url = f"https://picsum.photos/seed/{dept_name}/800/600"
    
    try:
        # 下载图片
        response = requests.get(image_url, timeout=10)
        response.raise_for_status()
        
        # 保存图片
        file_name = f"department{index}.jpg"
        file_path = os.path.join(download_dir, file_name)
        
        with open(file_path, "wb") as f:
            f.write(response.content)
        
        print(f"成功下载{dept_name}图片：{file_name}")
        return file_name
    except Exception as e:
        print(f"下载{dept_name}图片失败：{str(e)}")
        return None

# 下载所有科室图片
for i, dept in enumerate(departments, 1):
    download_department_image(dept, i)

print("所有科室图片下载完成！")
