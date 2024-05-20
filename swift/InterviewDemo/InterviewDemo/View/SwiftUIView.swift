//

//
//  SwiftUIView.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/2/2.
//  
//
    

import SwiftUI

class ModelObject: ObservableObject {
    @Published var isRefreshing: Bool = false {
        didSet {
            if isRefreshing {
                self.page = 1
                //刷新发起网络请求
                requestData(self.page)
            }
        }
    }
    @Published var isLoadingMore: Bool = false {
        didSet {
            if isLoadingMore {
                if self.page < totalPage {
                    self.page += 1
                }else{
                    self.isLoadingMore = false
                    return
                }
                //刷新发起网络请求
                requestData(self.page)
            }
        }
    }
    
    @Published var array:[String] = []
    
    var page:Int = 1
    let totalPage: Int = 3
    
    func requestData(_ page:Int = 1) {
        DispatchQueue.main.asyncAfter(deadline: .now() + 2.0) {
            if page == 1 {
                self.array = Array(0...15).map { "Hello, world! - \($0) - \(page)" }
            }else {
                let items = Array(0...15).map { "Hello, world! - \($0) - \(page)" }
                self.array.append(contentsOf: items)
            }
            self.isRefreshing = false
            self.isLoadingMore = false
        }
    }
}

struct ListView: View {
    @ObservedObject var modelObject = ModelObject()
    
    init() {
        modelObject.requestData()
    }
    
    var body: some View {
        NavigationView {
            List(modelObject.array, id: \.self) { text in
                Text(text).padding()
          }
          .addRefreshFooter(isLoadingMore: $modelObject.isLoadingMore,
                            noMoreText:modelObject.page >= modelObject.totalPage ? "没有更多数据了":nil)
          .navigationBarTitle("刷新demo")
        }
    }
}

struct SwiftUIView_Previews: PreviewProvider {
    static var previews: some View {
        ListView()
    }
}
