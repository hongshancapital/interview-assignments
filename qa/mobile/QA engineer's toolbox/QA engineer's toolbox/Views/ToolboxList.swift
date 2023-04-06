//
//  ToolboxList.swift
//  QA engineer's toolbox
//
//  Created by Boxiang Yu - Ciic on 2021/1/5.
//

import SwiftUI

struct ToolboxList: View {
    
    @EnvironmentObject var model: ToolboxListModel;
    
    var body: some View {
        VStack{
            Text("QA Engineer's Toolbox Arsenal:").font(.title2)
            List{
                ForEach(model.results, id: \.self) { tool in
                    HStack(alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/, spacing: 20, content: {
                        Text(tool.name).font(.system(size: 16))
                        Text(":")
                        Text(tool.tools).font(.system(size: 16)).bold()
                    }).border(width: 1, edges: [.leading], color: Color(hex: 0x50b855))
                }
            }
        }
    }
}

struct ToolboxList_Previews: PreviewProvider {
    static var previews: some View {
        let list = ToolboxListModel(results: [ToolboxModel(id: 3, name: "Tom", tools: "E2E"), ToolboxModel(id: 1, name: "Jerry", tools: "Jest")], options: ["E2E", "Jest", "Selenium"]);
        return ToolboxList().environmentObject(list)
    }
}
