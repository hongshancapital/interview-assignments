//
//  MylistRow.swift
//  MyHomework
//
//  Created by mao on 2022/6/10.
//

import SwiftUI
import SDWebImageSwiftUI


class ActiveGoalsViewModel : ObservableObject {

}


struct MylistRow: View {
    @State var myObjc: MylistObj!
    

    var body: some View {
        
        HStack{
            HStack{
                let urled : URL = URL(string: myObjc.artworkUrl100)!;

                WebImage(url: urled)
                    .placeholder{Color.gray}
                    .resizable()
                    .frame(width: 50, height: 50)
            }
            
            VStack(alignment: .leading) {
                
                Text(myObjc.artistName)
                    .font(.system(size: 20))

                HStack {
                    Text(myObjc.description)
                }
                .frame(height:50)
                .font(.subheadline)
                .foregroundColor(.secondary)
            }
            
            Spacer()
            
            HStack{
                Image(systemName: "heart.fill")
                
                    
                .foregroundColor(
                    myObjc.isLike == 0 ? SwiftUI.Color.gray : SwiftUI.Color.red)
                
                    
               .onTapGesture {
                        if myObjc.isLike == 0{
                            myObjc.isLike = 1
                        }else{
                            myObjc.isLike = 0
                        }
                
                   myListData.remove(at: Int(myObjc.id)!)
                   myListData.insert(myObjc, at: Int(myObjc.id)!);
                
               }
            }
        }
    }
}

struct MylistRow_Previews: PreviewProvider {
    static var previews: some View {
        MylistRow()
    }
}
