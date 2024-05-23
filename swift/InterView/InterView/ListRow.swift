//
//  ListRow.swift
//  InterView
//
//  Created by 黑旭鹏 on 2022/4/2.
//

import SwiftUI

struct ListRow: View {
    
    @State private var remoteImage : UIImage? = nil
    let placeholderOne = UIImage(named: "temp")
    
    @State private var select : String = "unselect"
    
    @State var isClicked: Bool = false

    var model: Model
    var body: some View {
        VStack{
            HStack (alignment: .top){
                if (remoteImage != nil) {
                    Image(uiImage: self.remoteImage ?? placeholderOne!)
                        .resizable()
                        .cornerRadius(15)
                        .onAppear(perform: fetchRemoteImage)
                        .frame(width: 70, height: 70)
                        .padding(.leading, 15)
                }else{
                    ProgressView()
                        .onAppear(perform: fetchRemoteImage)
                        .frame(width: 70, height: 70)
                        .padding(.leading, 15)
                }
                
                VStack (alignment: .leading) {
                    Text(model.trackCensoredName)
                        .font(.headline)
                        .lineLimit(1)
                    
                    Spacer()
                    Text(model.description)
                        .lineLimit(2)
                }
                .frame(
                  maxWidth: .infinity,
                  maxHeight: .infinity,
                  alignment: .topLeading
                  )
                
                Button(action: {
                    if select == "unselect" {
                        select = "select";
                        self.isClicked = true
                    }else{
                        select = "unselect";
                    }
                }) {
                    Image(select)
                        .resizable()
                        .scaleEffect(self.isClicked ? 1.2 : 1)
                        .animation(Animation.linear, value: 2)
                }
                .frame(
                    width: 20, height: 20)
                .padding([.top, .bottom])
                .padding(.top, 10)
                .padding(.trailing, 15)
            }
            .background(.white)
            .padding(.top, 10)
            .padding(.bottom, 10)
            
        }
        .background(.white)
        .cornerRadius(10)
        
    }
    
    func fetchRemoteImage()
    {
        guard let url = URL(string: model.artworkUrl512) else { return }
        URLSession.shared.dataTask(with: url){ (data, response, error) in
            if data != nil {
                if let image = UIImage(data: data!){
                    self.remoteImage = image
                }
                else{
                    print(error ?? "")
                }
            }else{
                print(error ?? "")
            }
        }.resume()
    }
}

struct ListRow_Previews: PreviewProvider {
    static var previews: some View {
        ListRow(model: modelData.results[0])
    }
}
