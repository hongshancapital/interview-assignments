//
//  RecordView.swift
//  search
//
//  Created by bc on 2020/9/9.
//  Copyright © 2020 sc. All rights reserved.
//

import SwiftUI
import Foundation

struct RecordView: View {
    var model: RecordViewModel
    
    var bgColor: Color{
        if self.model.record.status == .instock{
            return Color("instockbg")
        }else{
            return Color("outstockbg")
        }
    }
    
    var figureColor: Color{
        if self.model.record.status == .instock{
            return Color("instockfg")
        }else{
            return Color("outstockfg")
        }
    }
    
    var body: some View {
        VStack{
            HStack(alignment: .top){
                VStack(alignment: .leading){
                    Text(self.model.model)
                        .font(.system(size: 18, weight: .bold))
                        .foregroundColor(Color("textdarkgray"))
                    Text(self.model.status)
                        .foregroundColor(Color("textlightgray"))
                        .font(.system(size: 12))
                        .padding(.init(top: 5, leading: 0, bottom: 5, trailing: 0))
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding()
                HStack(alignment: .center){
                    VStack(alignment: .trailing){
                        Text("$\(self.model.price)")
                            .font(.system(size: 12))
                            .foregroundColor(figureColor)
                            .padding(.horizontal, 10)
                            .padding(.vertical, 5)
                        
                        
                    }            .background(
                        RoundedRectangle(cornerRadius: 8)
                            .fill(bgColor)
                    ).padding(.trailing)
                }.frame(maxHeight: .infinity)
                
            }.frame(maxWidth: .infinity, maxHeight: 70)
            
        }
    }
    
}

#if DEBUG
extension RecordViewModel{
    
    static func sample() -> RecordViewModel{
        return RecordViewModel(record: Record(type: "Vacuum", brand: "Dyson", model: "V11", id: UUID().uuidString, price: "399", status: .instock))
    }
}
struct RecordView_Previews: PreviewProvider {
    static var previews: some View {
        RecordView(model: .sample())
        //        BindingViewExamplePreviewContainer_2()
    }
}
#endif
