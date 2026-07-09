//
//  TableRow.swift
//  text
//
//  Created by Harden.L on 2023/5/23.
//

import SwiftUI

struct TableRow : View {
     
    @ObservedObject private var art : Artist
    var favorite: ((_ art:Artist) -> Void)
    
    init(art: Artist,favor: @escaping (_ art:Artist) -> Void) {
        self.art = art
        self.favorite = favor
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0){
            ContextView(art: art,favor: favorite).frame(alignment: .leading)
        }.frame(width: ScreenW - 30)
    }
}

struct ContextView : View {
//    private let iconUrl:String
//    private let title: String
//    private let subTitle: String?
    @State var select : Bool = false
    // 点击了
    var favorite: ((_ art:Artist) -> Void)?
    @State private var loading:Bool = true
    
    @State private var remoteImage : UIImage? = nil //该属性拥有@State标记，所以当该属性的值发生变化时，和改属性绑定的图像视图，将立即显示新的图像内容
       
    let placeholderOne = UIImage(named: "hand") //占位图
    
    @ObservedObject private var art : Artist
    
    init(art: Artist,favor: @escaping (_ art:Artist) -> Void) {
        self.art = art
        self.select = art.select
    }
    
    
    var body: some View {
        HStack(alignment:.center,spacing: 10){
            ZStack(alignment: .leading){
                Image(uiImage: self.remoteImage ?? placeholderOne!) //如果网络图片属性的值不为空，则显示下载后的网络图片，否则显示占位符图片
                    .onAppear(perform: fetchRemoteImage) //当图片显示之后，将立即执行下载网络图片的
                if loading {
                    GCIndicatorView(isShowing: $loading, style: .large)
                }
            }.disabled(loading).cornerRadius(10).frame(width: 60,height: 60)
            
            VStack(alignment: .leading, spacing: 5) {
                
                Text(art.name).bold().multilineTextAlignment(.leading).lineLimit(1)
                Text(art.description).font(.subheadline).opacity(0.5).lineLimit(2).multilineTextAlignment(.leading)
            }
            Spacer(minLength: 0)
            Button(action: addItem) {
                Image(systemName: art.select ? "star.fill" : "star")
            }
            
        }.frame( alignment: .leading).background(Color.clear).padding(10)
    }
    
    func addItem(){
//        art.select = !art.select
        if nil != favorite{
            favorite!(art)
        }
    }
    func fetchRemoteImage() //用来下载互联网上的图片
        {
            self.loading = true
            guard let url = URL(string: art.workUrl) else { return } //初始化一个字符串常量，作为网络图片的地址
            URLSession.shared.dataTask(with: url){ (data, response, error) in //执行URLSession单例对象的数据任务方法，以下载指定的图片
                self.loading = false
                if let image = UIImage(data: data!){
                    self.remoteImage = image //当图片下载成功之后，将下载后的数据转换为图像，并存储在remoteImage属性中
                }
                else{
                    print(error ?? "") //如果图片下载失败之后，则在控制台输出错误信息
                }
            }.resume() //通过执行resume方法，开始下载指定路径的网络图片
        }
}
