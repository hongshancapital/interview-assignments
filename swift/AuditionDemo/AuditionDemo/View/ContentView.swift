//
//  ContentView.swift
//  AuditionDemo
//
//  Created by dian bao on 2022/7/28.
//

import SwiftUI

//MARK: -展现业务数据View
struct ContentView: View {
    //MARK: - 属性
    @ObservedObject
    var plManager:PageLoaderManager = PageLoaderManager.sharedInstance          //分页加载器
    @ObservedObject
    var nm = NetMonitor.sharedInstance                                          //网络监听器
    @StateObject
    var bp:BusinessProvider = BusinessProvider()                                //业务数据提供者
    
    //MARK: - Body
    var body: some View {
        ZStack{
            Group{
                if( (plManager.LoadingBegin == false && bp.state == .LOADING )                              //数据正在加载中且还没有开始加载分页
                    || (bp.state == .FAIL && bp.LoadErrorCode == NetResourceLoader.ERROR_NET_UNCONNECT && nm.netIsOpen == true) )   //当前正在加载失败状态且之前失败原因是因为网络未连接造成，但此时网络已经恢复连接将重新从服务器获取新数据
                {
                    ProgressView("")
                        .progressViewStyle(.circular)
                        .padding()
                        .onAppear(){
                            //这里做延时操作可以在性能好的手机上更容易看到一开始的全屏加载图标动画
                            DispatchQueue.global().asyncAfter(deadline:DispatchTime.now()+(Util.sharedInstance.forSimulation() == true ? 1.5:0) ){
                                bp.loadData(sUrl:Util.sharedInstance.demoUrl())
                            }
                        }
                        .frame(alignment: .center)
                        .offset(x: 0, y: UIScreen.main.bounds.height/2-(FrameWorkView.titleBarHeight+FrameWorkView.bigTitleHeight+Util.sharedInstance.statusBarHeight()))
                }
                else if( plManager.PageEndIndex > 0 )                               //分页加载器已经加载到分页
                {
                    //使用懒加载，确保滑动到底部能进入RefreshView的OnAppear
                    LazyVStack(spacing:0) {
                        ForEach(0..<plManager.PageEndIndex,id: \.self){index in
                            ItemView(dict: plManager.results[index])
                                .padding(EdgeInsets.init(top: 5, leading: 0, bottom: 5, trailing: 0))
                        }
                        //底部Loading视图
                        LoadView()
                    }
                    .padding(EdgeInsets.init(top: 10, leading: 20, bottom: 10, trailing: 20))
                }
                else                                                               //其他情况
                {
                    EmptyView()
                }
            }//: Group
        }//: ZStack
        .padding(0)
        .alert(isPresented:$bp.needWarn )
        {
            Alert(title: Text("警告"),
                  message: Text(bp.description),
                  dismissButton: .default(Text("确认")))
        }
    }
}

//MARK: - Preview
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
