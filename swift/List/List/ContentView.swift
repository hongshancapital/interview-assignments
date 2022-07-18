//
//  ContentView.swift
//  List
//
//  Created by wangmingyou on 2022/7/17.
//

import SwiftUI

let ScreenWidth = UIScreen.main.bounds.width
let ScreenHeight = UIScreen.main.bounds.height
let ListMaxCount = 45

struct ContentView: View {
    @State var isRefreshing = false
    @State var page = 0
    @State var dataArr: [CellModel] = []
    @State var selectedIndexes: [UInt] = [UInt]()
    
    var body: some View {
        NavigationView (content: {
            list
            .navigationTitle(Text("App"))
            
        }).background(.gray.opacity(0.1))
    }
    
    // MARK: LoadData
    private func loadData() {
        isRefreshing = true
        // 模拟网络请求的loading duration
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.35) {
            loadLocalData()
        }
    }
    
    private func loadLocalData(count: Int = 20) {
        LoadData().loadLocalData(count: count) { result in
            isRefreshing = false
            switch result {
            case .success(let data):
                dataArr.append(contentsOf: data)
            case .failure(_):
                break;
            }
        }
    }
    
    // MARK: UI
    private var list: some View {
        List {
            LazyVStack {
                ForEach(0..<dataArr.count, id: \.self) { i in
                    HStack() {
                        Spacer()
                        AsyncImage(url: URL.init(string: dataArr[i].artworkUrl60)) { img in
                            img.resizable()
                        } placeholder: {
                            ProgressView()
                        }
                        .frame(width: 68, height: 68).cornerRadius(16)
                        VStack(spacing: 8) {
                            HStack() {
                                Text("\(dataArr[i].sellerName)").font(.title2)
                                Spacer()
                            }
                            HStack {
                                Text(dataArr[i].description).font(.caption).lineLimit(2)
                                Spacer()
                            }
                        }
                        Spacer()
                        
                        if !selectedIndexes.contains(UInt(i)) {
                            Button {
                                print("\(i)")
                                selectedIndexes.append(UInt(i))
                            } label: {
                                Image(systemName: "heart")
                            }.frame(width: 30, height: 30)
                                .buttonStyle(BorderlessButtonStyle())
                                .accessibility(identifier: "heartBtn\(i)")
                        } else {
                            Button {
                                selectedIndexes.removeAll { $0 == UInt(i) }
                            } label: {
                                Image(systemName: "heart.fill")
                            }.frame(width: 30, height: 30)
                                .buttonStyle(BorderlessButtonStyle())
                        }
                        
                        Spacer()
                    }
                    .frame(width: ScreenWidth - 20, height: 90, alignment: .center)
                    .background(.white).cornerRadius(10).padding(EdgeInsets.init(top: 0, leading: 10, bottom: 0, trailing: 10))
                }
                if dataArr.count >= ListMaxCount {
                    HStack {
                        Text("No more").padding(EdgeInsets.init(top: 0, leading: 5, bottom: 0, trailing: 0)).frame(height: 35)
                    }
                } else {
                    //上拉加载更多
                    refreshListener
                }
                
            }
            .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0)).listRowSeparator(.hidden).background(.gray.opacity(0.1))
            
        }
        .listStyle(.inset).padding(EdgeInsets.init(top: 0, leading: 0, bottom: 0, trailing: 0))
        .refreshable(action: {//下拉刷新
            dataArr.removeAll()
            loadData()
        })
        .onAppear {
            loadData()
        }
    }
    
    private var refreshListener: some View {
        HStack {
            ProgressView()
            Text(isRefreshing ? "Refreshing" : "Loading...").padding(EdgeInsets.init(top: 0, leading: 5, bottom: 0, trailing: 0)).frame(height: 35)
        }
        .onAppear(perform: {
            page += 1
            loadData()
        })
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

struct LoadData {
    func loadLocalData(count: Int = 20, completion: @escaping (Result<([CellModel]), NSError>) -> Void) {
        guard let path = Bundle.main.path(forResource: "data", ofType: "json"), let localData = NSData.init(contentsOfFile: path) as? Data else {
            completion(.failure(NSError(domain: "parse error", code: -100000)))
            return
        }
        do {
            let json = try JSONSerialization.jsonObject(with: localData, options: .mutableContainers)
            if let dic = json as? [String : Any],
                let results = dic["results"] as? [[String : Any]] {
                let decoder = JSONDecoder()
                var newDatas = [CellModel]()
                for (i, result) in results.enumerated() {
                    if i < count {
                        let json = try JSONSerialization.data(withJSONObject: result, options: [])
                        let cellModel: CellModel = try decoder.decode(CellModel.self, from: json)
                        newDatas.append(cellModel)
                    }
                }
                completion(.success(newDatas))
            }
        } catch {
            completion(.failure(NSError(domain: "catch error", code: -100001)))
        }
    }
}

struct CellModel: Codable {
    var artworkUrl60: String
    var sellerName: String
    var description: String
}
