//
//  ContentView.swift
//  swiftui
//
//  Created by 聂高涛 on 2022/3/1.
//

import SwiftUI

struct CellData: Identifiable, Hashable {
    var id = UUID()
    var icon = ""
    var title = ""
    var subtitle = ""
    var isSelected = false
}

struct CellView : View {
    @State var data : CellData
    var body: some View {
        return HStack.init(alignment: .center, spacing: 10) {
            Image(data.icon).resizable().frame(width: 40, height: 40).cornerRadius(4)
            VStack.init(alignment: .leading, spacing: 4) {
                Text(data.title).font(.system(size: 16))
                Text(data.subtitle).font(.system(size: 13))
            }
            Spacer()
            Button(action:{
                self.data.isSelected = !self.data.isSelected;
            }) {
                Image(data.isSelected ? "loveselected":"loveunselected").resizable().frame(width: 28, height: 28).cornerRadius(4)
            }
        }
    }
}


struct ContentView: View {
    @State private var dataArray = [
        CellData(icon: "image", title: "1.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "2.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "3.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "4.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "5.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "6.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "7.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "8.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "9.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "10.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "11.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "12.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "13.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "14.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "15.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "16.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "17.这里是主标题", subtitle: "这里是副标题", isSelected: false),
        CellData(icon: "image", title: "18.这里是主标题", subtitle: "这里是副标题", isSelected: false),
    ]
    
    @Binding var isDone: Bool
    
    init(){
        UITableView.appearance().separatorStyle = .none
        UITableViewCell.appearance().accessoryType = .none
    }
    
    var body: some View {
        return NavigationView{
            List(dataArray, id:\.self){ data in
                Section(){
                    CellView(data: data).padding(EdgeInsets(top: 5, leading: 0, bottom: 5, trailing: 0))
                }
            }.navigationBarTitle(Text("首页"), displayMode:.large)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}




struct DetailView : View {
    var body: some View {
        return Text("DetailView")
    }
}
