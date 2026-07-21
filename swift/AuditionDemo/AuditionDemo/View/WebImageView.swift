//
//  WebImageView.swift
//  AuditionDemo
//
//  Created by dian bao on 2022/7/28.
//

import SwiftUI
import Combine

//MARK: -网络图标View
struct WebImageView: View {
    //MARK: - 属性
    var urls:[URL]                              //备选图片URL，已按优先级排序
    @State
    var UrlIndex = 0                            //待加载的图片URL序号
    @StateObject
    var viewModel = ImageProvider()             //图片提供者，生命周期和此View绑定
    @ObservedObject
    var nm = NetMonitor.sharedInstance          //网络监听器
    
    //MARK: - Body
    var body: some View {
        ZStack(alignment: .center)
        {
            Group{
                if( viewModel.state == LoadContentState.LOADING                                                                           //当前正在加载状态
                    || (viewModel.state == LoadContentState.FAIL && viewModel.LoadErrorCode == -1009 && nm.netIsOpen == true)       //当前正在加载失败状态且失败原因是因为网络不通造成，但此时网络已经恢复畅通
                )
                {
                    HStack(alignment: .center){
                        Spacer()
                        ProgressView("")
                            .progressViewStyle(.circular)
                            .padding(.top,10)
                            .onAppear {
                                if( self.UrlIndex < urls.count)
                                {
                                    //这里做延时操作可以在性能好的手机上更容易看到图片加载动画
                                    DispatchQueue.global().asyncAfter(deadline:DispatchTime.now()+(Util.sharedInstance.forSimulation() == true ? 1.5:0)){
                                        viewModel.loadImage(url: urls[UrlIndex])
                                    }
                                }
                                else
                                {
                                    viewModel.state = LoadContentState.FAIL
                                }
                            }
                            .padding()
                            .frame(alignment: .center)
                        
                        Spacer()
                    }//: HStack
                }
                else                                                                                        //其余情况
                {
                    Button(action: {
                        if( viewModel.state == LoadContentState.FAIL )                                      //提供手动点击重新加载机会
                        {
                            //如果之前加载网络图片失败，则点击重新加载网络图片
                            viewModel.state = LoadContentState.LOADING
                        }
                    }, label: {
                        if( viewModel.state == LoadContentState.SUCCESS )                                     //加载成功
                        {
                            Image(uiImage: viewModel.image != nil ? viewModel.image! : UIImage.init())
                                .resizable()
                                .frame(alignment: .center)
                                .overlay(
                                    RoundedRectangle(cornerRadius: 12)
                                        .stroke(Color("#e6e6e6".uicolor()), lineWidth: 2)
                                )
                        }
                        else                                                                                //加载失败
                        {
                            Image(uiImage: viewModel.image != nil ? viewModel.image! : UIImage.init())
                                .resizable()
                                .frame(alignment: .center)
                                .onAppear {
                                    if( urls.count > 0 )
                                    {
                                        UrlIndex = (UrlIndex + 1)%urls.count                      //如果当前网络可连接，可以按顺序换网络图标URL重试下
                                    }
                                    if( nm.netIsOpen == true )                                    //判断当前网络是否可连接
                                    {
                                        viewModel.state = LoadContentState.LOADING                //如果当前网络可连接，可以按顺序换网络图标URL重试下
                                        DispatchQueue.global().asyncAfter(deadline:DispatchTime.now()+(Util.sharedInstance.forSimulation() == true ? 1.5:0)){
                                            viewModel.loadImage(url: urls[UrlIndex])
                                        }
                                    }
                                }
                        }
                    })//: Button
                    .cornerRadius(viewModel.state == LoadContentState.SUCCESS ? 12:0)
                    .padding()
                    .disabled( (viewModel.state == LoadContentState.SUCCESS || urls.count == 0 ) ? true:false)
                }
            }//: Group
        }//: ZStack
        .frame(width: 96, height: 96, alignment: .leading)
    }
}

//MARK: - Preview
struct WebImageView_Previews: PreviewProvider {
    static var previews: some View {
        WebImageView(urls: [URL.init(string: "https://is5-ssl.mzstatic.com/image/thumb/Purple112/v4/d1/30/f0/d130f023-a584-ec38-356e-27165f7b9912/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/512x512bb.jpg")!])
    }
}
