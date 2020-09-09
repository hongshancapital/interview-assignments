//
//  SearchView.swift
//  search
//
//  Created by bc on 2020/9/9.
//  Copyright © 2020 sc. All rights reserved.
//

import SwiftUI
#if canImport(UIKit)
extension View {
    func hideKeyboard() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}
#endif
struct SearchView: View {
    let timer = Timer.publish(every: 0.5, on: .main, in: .common).autoconnect()
    
    @State var isEditing = false
    @State var key = ""
    @State var lastKey = ""
    
    @State var searching = false
    @State var searched = false
    @State var resultsBySection: [String:[RecordViewModel]] = [:]
    
    
    var body: some View {
        VStack(alignment: .leading){
            TextField("Tap here to search", text: $key)
                .padding(9)
                .padding(.horizontal, 25)
                .background(Color("searchbarbg"))
                .cornerRadius(9)
                .overlay(
                    HStack {
                        Image(systemName: "magnifyingglass")
                            .foregroundColor(.gray)
                            .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                            .padding(.leading, 9)
                        
                        if self.isEditing {
                            Button(action: {
                                self.key = ""
                                self.searched = false
                                self.resultsBySection = [:]
                            }) {
                                Image(systemName: "multiply.circle.fill")
                                    .foregroundColor(.gray)
                                    .padding(.trailing, 9)
                            }
                        }
                    }
            )
                .padding(.horizontal, 10)
                .onTapGesture {
                    self.isEditing = true
                    
            }
            
            if self.searching {
                Text("Searching...")
            }
            if self.resultsBySection.isEmpty {
                if self.searched {
                    
                    HStack{
                        Text("No result").font(.system(size: 24)).foregroundColor(Color(hex: 0x999999))
                            .padding(.top, 50)
                    }.frame(maxWidth: .infinity)
                    
                }
                Spacer()
            }else{
                ForEach(self.resultsBySection.keys.sorted(by: {$0 > $1}), id: \.self){type in
                    Section(header: HStack {
                        Text(type).font(.system(size: 24)).foregroundColor(Color("sectionheaderfg")).padding(15)
                    }){
                        ForEach(self.resultsBySection[type]!, id: \.id){ rvm in
                            RecordView(model: rvm).background(Color(.white))
                        }
                    }
                }
                Spacer()
            }
            
            
        }.onReceive(timer) { time in
            if self.key == self.lastKey {
                //                print("no new input, ignore")
            }else if "" != self.key.replacingOccurrences(of: " ", with: ""){
                print("Performing search for \(self.key)")
                self.searching = true
                self.searched = false
                self.lastKey = self.key
                DispatchQueue.global(qos: .utility).async{
                    self.performSearch()
                }
            }
        }
        
        
    }
    
    fileprivate func performSearch(){
        let session = URLSession.shared
        let url = URL(string: "http://127.0.0.1:4000/search?key=\(self.key.replacingOccurrences(of: " ", with: ""))")!
        let task = session.dataTask(with: url, completionHandler: { data, response, error in
            
            //            print(response)
            if error != nil {
                self.searched = true
                self.searching = false
                print(error)
                return
            }
            //            print(String(data: data!, encoding: .utf8))

            do {
                
                let json = try JSONDecoder().decode([Record].self, from: data! )
                //try JSONSerialization.jsonObject(with: data!, options: [])
                
                self.resultsBySection = self.assembleResultByType(raw: json)
                //                print(json)
                //                self.hideKeyboard()
            } catch {
                print("Error during JSON serialization: \(error.localizedDescription)")
            }
            
        })
        task.resume()
        self.searched = true
        self.searching = false
        
    }
    
    fileprivate func assembleResultByType(raw: [Record]) -> [String:[RecordViewModel]]{
        var r:[String:[RecordViewModel]] = [:]
        
        for rec in raw{
            if r[rec.type] != nil{
                r[rec.type]!.append(RecordViewModel(record: rec))
            }else{
                r[rec.type] = [RecordViewModel(record: rec)]
            }
        }
        
        return r
    }
}



#if DEBUG

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        return SearchView()
//        do {
//            let data = Record.sampleString()
//            let json = try JSONDecoder().decode([Record].self, from: Data(data.utf8) )
//
//            sv.resultsBySection = sv.assembleResultByType(raw: json)
//        } catch {
//            print("Error during JSON serialization: \(error.localizedDescription)")
//        }
//
//        sv.performSearch()
//        return sv
    }
}
#endif
