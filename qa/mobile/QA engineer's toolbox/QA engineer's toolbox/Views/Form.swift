//
//  Form.swift
//  QA engineer's toolbox
//
//  Created by Boxiang Yu - Ciic on 2021/1/5.
//

import SwiftUI

struct Form: View {
    @State private var name: String = ""
    @State private var tools: String = ""
    @State private var optionsIndex: Int = 0
    
    @EnvironmentObject var model: ToolboxListModel;

    var body: some View {
        VStack{
            TextField("Please enter Engineer's name", text: $name).frame(height: 80)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .cornerRadius(16)
                .padding([.leading, .trailing], 24)
            Picker(selection: $optionsIndex, label: Text("Choose a tool")) {
                ForEach(0..<model.options.count, id:\.self) { index in
                    Text(model.options[index]).tag(index)
                }
            }.pickerStyle(SegmentedPickerStyle())
            RoundButton(title: "Submit",
                        backgroundColor: Color(hex: 0x50b855),
                        foregroundColor: .white,
                        roundRadius: 24
                        ) {
                self.tools = model.options[optionsIndex]
                model.createToolboxPref(name: self.name, tools: self.tools)
                model.loadData()
            }
        }
    }
}

struct Form_Previews: PreviewProvider {
    static var previews: some View {
        let list = ToolboxListModel(results: [ToolboxModel(id: 3, name: "Tom", tools: "E2E"), ToolboxModel(id: 1, name: "Jerry", tools: "Jest")], options: ["E2E", "Jest", "Selenium"]);
        Form().environmentObject(list)
    }
}
