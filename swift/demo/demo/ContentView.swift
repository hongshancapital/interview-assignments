//
//  ContentView.swift
//  demo
//
//  Created by 朱伟 on 2022/2/11.
//

import SwiftUI

struct ContentView: View {
    @EnvironmentObject private var dataManage: DataManager
    @State var hasLoad = false
    @State var hasMore = true

    
    var body: some View {
        NavigationView{
            Group{
                if (hasLoad) {
                    List{
                        ForEach (dataManage.list) { item in
                            HStack{
                                AsyncImage<ProgressView>(
                                    url: URL(string: item.image)!,
                                    placeholder: { ProgressView() }
                                ).aspectRatio(contentMode: .fit)
                                    .frame(width: 44, height: 44)
                                    .cornerRadius(5)
                                VStack(alignment: .leading){
                                    Text(item.title)
                                        .font(.system(size: 16))
                                        .bold()
                                    Text(item.des)
                                        .lineLimit(2)
                                        .font(.system(size: 13))
                                }
                                Spacer()
                                Image(systemName:item.like ? "suit.heart.fill" : "suit.heart")
                                    .foregroundColor(item.like ? Color.red : Color.gray)
                                    .onTapGesture {
                                        print("print \(item.title)")
                                        item.like = !item.like
                                    }
                            }
                            .padding(10)
                            .listRowSeparator(.hidden)
                            .background(.white)
                            .cornerRadius(10)
                        }
                        .padding(.leading,15)
                        .padding(.top,5)
                        .padding(.bottom,5)
                        .padding(.trailing,15)
                        .background(ColorBackGround)
                        .listRowInsets(EdgeInsets())
                        
                        Group{
                            if (hasMore) {
                                ProgressView()
                            }else {
                                Text("No more data...")
                            }
                        }
                        .frame(width: UIScreen.main.bounds.width,height:50, alignment: .center)
                        .listRowSeparator(.hidden)
                        .listRowInsets(EdgeInsets())
                        .background(ColorBackGround)
                        .onAppear{
                            start = start + 1
                            loadData()
                        }
                    }
                    .refreshable{
                        start = 0
                        hasMore = true
                        loadData()
                    }
                    .background(ColorBackGround)
                    .listStyle(PlainListStyle())
                }else{
                    ProgressView()
                }
            }
            .navigationBarTitle(Text("App"))
        }
        .onAppear{
            loadData()
        }
        
    }
    
    @State private var start:Int = 0
    
    func loadData(){
        if start > 1 {
            hasMore = false
            return
        }
        let url = "https://itunes.apple.com/search?entity=software&limit=50&term=chat"
        var requset = URLRequest(url:URL(string:url)!)
        requset.httpMethod = "GET"
        requset.setValue("UTF-8", forHTTPHeaderField:"Charset")
        requset.setValue("application/json", forHTTPHeaderField:"Content-Type")
        URLSession.shared.dataTask(with: requset){(data,response,Error)in
            do{
                if let result = try? JSONDecoder().decode(DataModel.self, from: data!) {
                    print(result.results)
                    DispatchQueue.main.async {
                        hasLoad = true
                        if (start == 0) {
                            dataManage.list.removeAll()
                        }
                        dataManage.list = dataManage.list + result.results.map { item in
                            return ShowItem(image: item.artworkUrl60, title: item.trackCensoredName, des: item.description)
                        }
                     }
                }
            }
        }.resume()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
