<template>
    <v-container fluid>
<!-- options -->
    <!-- align -->
    <!-- fill -->
    <!-- spacing -->
    <!-- filling character -->
    <!-- languages -->
    <!-- mutiline? -->
    <!-- length -->

    <!-- input -->
        <!-- editText -->
    <v-layout column>
    <v-flex xs12>
    <v-card>
        <v-card-title primary-title>
            <div>
                <h3 class="headline mb-0">Original Comment</h3>
                <!-- <div>Located two hours south of Sydney in the <br>Southern Highlands of New South Wales, ...</div> -->
            </div>
        </v-card-title>
        <v-card-text>
            <v-text-field v-model="inputText" v-on:change="updateInput()" v-on:input="updateInput()"
          multi-line
          id="input"
          name="input-text"
          label="Put Your Comment Here"
        ></v-text-field>
        </v-card-text>
        <v-card-actions>
            <v-btn v-on:click="clearInput()">
                Clear!
            </v-btn>
            <v-btn v-on:click="updateInput()">
                Generate!
            </v-btn>
        </v-card-actions>
    </v-card>
    </v-flex>
    <br>
    <v-flex xs12>
    <v-card>
        <v-card-text>
            <v-flex xs12>
                <v-text-field v-model="outputText"
                class="monotext"
                disabled
                multi-line
                id="output"
                name="output-text"
                ></v-text-field>
            </v-flex>
        </v-card-text>
        <v-card-actions>
        <!-- buttons -->
            <v-btn>
                CopyToClipboard
            </v-btn>
        </v-card-actions>
    </v-card>
    </v-flex>
    </v-layout>
    <!-- output -->
        <!-- text output -->
        <!-- copy to clipboard -->
  </v-container>
</template>

<script>
const _ = require('lodash')

const defaultOptions = {
  align: 'left',
  character: '=',
  commentSingleLine: '//',
  spacing: 1,
  width: 80,
  wrap: false
}
/**
 * @param {string} origin - original commit message
 * @param {object} customOptions - comment generating options
 */
let generateComment = function (origin, customOptions) {
  let options = customOptions || _.clone(defaultOptions)
  let messages = origin.split('\n')
  const MAX_COMMENT_LENGTH_IN_LINE = options.width - options.spacing * 2 - options.commentSingleLine.length

  // calculate parameters
  if (options.wrap) {

  } else {
    let maxLength = MAX_COMMENT_LENGTH_IN_LINE
    messages.forEach((message) => {
      if (maxLength < message.length) {
        maxLength = message.length
        options.width = options.spacing * 2 + options.commentSingleLine.length + maxLength
        console.log('longest message is longer then width, so expend it to', options.width)
      }
    })
  }

  // comment build
  let border = ''
  border += options.commentSingleLine
  border += options.character.repeat(options.width - options.commentSingleLine.length)

  let output = ''
  output += border
  output += '\n'

  messages.forEach(message => {
    let commentLine = ''
    switch (options.align) {
      case 'left':
        commentLine += options.commentSingleLine
        commentLine += ' '.repeat(options.spacing)
        commentLine += message
        commentLine += ' '.repeat(options.spacing)
        commentLine += options.character.repeat(options.width - options.spacing * 2 - options.commentSingleLine.length - message.length)
        commentLine += '\n'
        break
      //   case 'middle':
      //   case 'right':
      default:
        break
    }
    console.log('commentLine', commentLine)
    output += commentLine
  })

  output += border
  return output
}
export default {
  data () {
    return {
      inputText: '',
      options: {
        align: 'middle'
      },
      outputText: '',
      updateInput () {
        this.outputText = generateComment(this.inputText)
      },
      clearInput () {
        this.inputText = ''
        this.outputText = ''
      }
    }
  }
}
</script>

<style>
.monotext {
    font-family: monospace;
}

</style>
