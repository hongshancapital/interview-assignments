//
//  ContentView.swift
//  TestSwiftUI
//
//  Created by 李辉 on 2022/12/5.
//

import SwiftUI
import UIKit

class FeedModel: Codable {
    var artworkUrl60: String
    var description: String
    var trackCensoredName: String
}

class ApiModel: Codable {
    var resultCount: Int
    var results:[FeedModel]
}

class CellViewModel: Identifiable{
    var artworkUrl60: String
    var description: String
    var trackCensoredName: String
    var respect: Bool
    init(_ artworkUrl60: String, _ description: String,_ trackCensoredName: String) {
        self.artworkUrl60 = artworkUrl60
        self.description = description
        self.trackCensoredName = trackCensoredName
        self.respect = false
    }
}


class LHViewModel {
    private var data: ApiModel?
    public var noMoreData: Bool = false
    var cellVMs: [CellViewModel] = []
     init() {
         self.data = loadData()
    }
  
    func refreashData() -> Void {
        noMoreData = false
        cellVMs.removeAll()
        loadMoreData(0)
    }
    
    func loadMoreData(_ page: Int) {
        cellVMs.removeAll()
        if page * 10 + 9 >= self.data!.resultCount - 1 {
            self.noMoreData = true
            return
        }
        
        for i in page * 10...page * 10 + 9 {
            let e:FeedModel = data!.results[i]
            cellVMs.append(CellViewModel.init(e.artworkUrl60, e.description, e.trackCensoredName))
        }
    }
    
   private func loadData() -> ApiModel? {
        let path = Bundle.main.path(forResource: "test", ofType: "json")
        let url = URL(fileURLWithPath: path!)
        do {
            let data = try Data(contentsOf: url)
            let model = try JSONDecoder().decode(ApiModel.self,from: data)
            return model
        } catch let error as Error? {
//            print("读取本地数据出现错误!",error ?? "")
            return nil
        }
    }
}

struct Cell: View {
    var vm: CellViewModel
    @State var artworkUrl60: String
    @State var trackCensoredName: String
    @State var respect: Bool
    init(_ vm: CellViewModel) {
        self.vm = vm
        self.artworkUrl60 = vm.artworkUrl60
        self.trackCensoredName = vm.trackCensoredName
        self.respect = vm.respect
    }
    var body: some View {
        HStack{
            AsyncImage.init(url: URL(string: vm.artworkUrl60)) { image in
                image.frame(width: 60, height: 60)
                    .scaledToFit()
                    .cornerRadius(10)
                    .padding(.leading,15)
                    .padding(.top,10)
                    .padding(.bottom,10)
                    .shadow(radius: 0.5)
            } placeholder: {
                ProgressView().frame(width: 60, height: 60)                 .padding(.leading,15)
                    .padding(.top,10)
                    .padding(.bottom,10)
                    .shadow(radius: 0.5)
            }

            VStack(alignment: .center, spacing: 5, content: {
                Text(vm.trackCensoredName).fontWeight(.bold).lineLimit(1).frame(maxWidth: .infinity, alignment: Alignment.leading).padding(.top,15)
                Text(vm.description).lineLimit(2)
                    .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: Alignment.leading)
                    .padding(.bottom,10)
                    .padding(.top,-5)
            })
            if self.respect {
                Button {
                    self.respect = false
                } label: {
                    Image("r_heart")
                }.padding(.trailing,10)
            } else {
                Button {
                    self.respect = true
                } label: {
                    Image("heart")
                }.padding(.trailing,10)
            }
        }
    }
}


struct Item: Identifiable {
    let id = UUID().uuidString
    let title: String
    let desc: String
    var image: String
}

@resultBuilder struct TestBuilder {
    static func buildBlock(_ items: Int...) -> [Int] {
        items
    }
}

struct ContentView: View {
    var viewModel: LHViewModel = LHViewModel.init()
    @State var cellVMs: [CellViewModel] = []
    @State var noMoreData = false
    var body: some View {
        NavigationView() {
        
            List{
                ForEach(cellVMs){item in
                        Cell.init(item).background(Color.white).cornerRadius(10)
                        .frame(maxWidth: .infinity, maxHeight: 90, alignment: Alignment.center).listRowBackground(Color.init(UIColor.systemGroupedBackground)).padding(.all,0).listRowSeparator(.hidden)
                }
                if self.cellVMs.count > 0 {
                    if !noMoreData {
                    
                        HStack(alignment: .center, spacing: 5, content: {
                            Spacer().frame( maxWidth:.infinity,  maxHeight: .infinity, alignment:.center).background(Color.init(UIColor.systemGroupedBackground)).padding(.all,-15)
                            ProgressView().frame( maxWidth:.infinity,  maxHeight: .infinity, alignment:.center).background(Color.init(UIColor.systemGroupedBackground)).padding(.all,-15)
                                .onAppear {
                                //模拟网络请求延时
                                    DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 0.3) {
                                    self.loadMoreData()
                                }
                            }
                            Text("Loading...").frame( maxWidth:150,  maxHeight: .infinity, alignment:.center).background(Color.init(UIColor.systemGroupedBackground)).foregroundColor(Color.gray).padding(.leading,-40)
                                .padding(.top,-15)
                                .padding(.bottom,-15)
                                .padding(.trailing,-15)
                            
                            Spacer().frame( maxWidth:.infinity,  maxHeight: .infinity, alignment:.center).background(Color.init(UIColor.systemGroupedBackground)).padding(.all,-15)
                            
                        })

                    } else {
                        Text("No more data").foregroundColor(Color.gray.opacity(0.5)).frame(maxWidth: .infinity, maxHeight: .infinity,alignment: Alignment.center).background(Color.init(UIColor.systemGroupedBackground)).padding(.all,-10)
                    }

                }else {
                    EmptyView()
                }
            }
                .listStyle(.plain)
                .listStyle(.sidebar)
                .background(Color.init(UIColor.systemGroupedBackground))
                .onAppear {
                    self.refreshData()
                }.refreshable {
                    self.refreshData()
                }
                .navigationBarTitle("App")
            
        }.navigationViewStyle(.stack)
    }
    
    func refreshData() {
        self.viewModel.refreashData()
        self.cellVMs.removeAll()
        for e in viewModel.cellVMs{
            self.cellVMs.append(e)
        }
        self.noMoreData = self.viewModel.noMoreData
    }
    
    func loadMoreData( ) {
        let p = self.cellVMs.count / 10
        self.viewModel.loadMoreData(p)
        self.noMoreData = self.viewModel.noMoreData
        for e in viewModel.cellVMs{
            self.cellVMs.append(e)
        }
    }

}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
//}
